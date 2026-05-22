(ns cicero.ai
  (:require
   [re-frame.core :as rf]
   [clojure.string :as str]))

;; Use standard js/fetch to call local ollama
(rf/reg-fx
 :ai/evaluate
 (fn [{:keys [player-text cicero-text backend api-key on-success on-failure]}]
   (let [system-prompt "You are 'The Consilium', an AI judging system evaluating expressionism.\nRespond ONLY with a JSON object containing two keys: 'score' (a number between 0 and 100) and 'explanation' (a VERY brief sentence, maximum 10 words, explaining the judgment in character).\nExample: {\"score\": 85, \"explanation\": \"Your language burns with urban dread and violent symbolism.\"}"
         user-prompt (str "Evaluate the following player speech compared to Cicero's text.\n\n"
                          "Cicero's text:\n" cicero-text "\n\n"
                          "Player's text:\n" player-text "\n\n"
                          "Criteria:\n"
                          "- emotional intensity\n"
                          "- imagery & surrealism\n"
                          "- fragmentation & chaos\n"
                          "- rhetorical aggression\n"
                          "- apocalyptic tone & metaphor density\n\n"
                     "Respond ONLY with a JSON object containing two keys: 'score' (a number between 0 and 100) and 'explanation' (a VERY brief sentence, maximum 10 words, explaining the judgment in character).\n"
                     "Example: {\"score\": 85, \"explanation\": \"Your language burns with urban dread and violent symbolism.\"}")
         combined-prompt (str system-prompt "\n\n" user-prompt)]

     
     (js/console.log "Starting AI evaluation request to" backend "...")
     
     (let [controller (js/AbortController.)
           timeout-ms 60000 ; 60 second timeout
           timeout-id (js/setTimeout #(.abort controller) timeout-ms)]
       
       (if (= backend :gemini)
         ;; --- GEMINI API CALL ---
         (if (empty? api-key)
           (do
             (js/clearTimeout timeout-id)
             (rf/dispatch (conj on-failure "Gemini API key is missing. Please set it in Settings.")))
           (-> (js/fetch (str "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.1-flash-lite:generateContent?key=" api-key)
                         #js {:method "POST"
                              :headers #js {"Content-Type" "application/json"}
                              :signal (.-signal controller)
                              :body (js/JSON.stringify
                                     #js {:systemInstruction #js {:parts #js [#js {:text system-prompt}]}
                                          :contents #js [#js {:parts #js [#js {:text user-prompt}]}]
                                          :generationConfig #js {:responseMimeType "application/json"
                                                                 :temperature 0.4}})})
               (.then (fn [response]
                        (js/clearTimeout timeout-id)
                        (if (.-ok response)
                          (.json response)
                          (throw (js/Error. (str "Gemini HTTP Error: " (.-statusText response)))))))
               (.then (fn [data]
                        (let [response-text (-> data .-candidates (aget 0) .-content .-parts (aget 0) .-text)
                              parsed (try
                                       (js/JSON.parse response-text)
                                       (catch :default e
                                         (let [cleaned (-> response-text
                                                           (str/replace #"(?si)^```json\s*" "")
                                                           (str/replace #"(?si)\s*```$" ""))]
                                           (try
                                             (js/JSON.parse cleaned)
                                             (catch :default e2
                                               #js {"score" 0
                                                    "explanation" "Gemini's mind fractured. (JSON Parse Error)"})))))]
                          (rf/dispatch (conj on-success (js->clj parsed))))))
               (.catch (fn [error]
                         (js/clearTimeout timeout-id)
                         (js/console.error "Gemini Evaluation Error:" error)
                         (rf/dispatch (conj on-failure (if (= (.-name error) "AbortError")
                                                         "Gemini took too long to respond."
                                                         (str "Gemini Error: " (.-message error)))))))))
         
         ;; --- OLLAMA API CALL ---
         (-> (js/fetch "http://localhost:11434/api/generate"
                       #js {:method "POST"
                            :headers #js {"Content-Type" "application/json"}
                            :signal (.-signal controller)
                            :body (js/JSON.stringify
                                   #js {:model "gemma4:26b"
                                        :prompt combined-prompt
                                        :stream false
                                        :format "json"
                                        :options #js {:temperature 0.4
                                                      :repeat_penalty 1.2
                                                      :num_predict 50}})})
             (.then (fn [response]
                      (js/clearTimeout timeout-id)
                      (if (.-ok response)
                        (.json response)
                        (throw (js/Error. (str "HTTP Error: " (.-statusText response)))))))
             (.then (fn [data]
                      (let [response-text (.-response data)
                            parsed (try
                                     (js/JSON.parse response-text)
                                     (catch :default e
                                       (let [cleaned (-> response-text
                                                         (str/replace #"(?si)^```json\s*" "")
                                                         (str/replace #"(?si)\s*```$" ""))]
                                         (try
                                           (js/JSON.parse cleaned)
                                           (catch :default e2
                                             #js {"score" 0
                                                  "explanation" "The Consilium's mind fractured. (JSON Parse Error)"})))))]
                        (rf/dispatch (conj on-success (js->clj parsed))))))
             (.catch (fn [error]
                       (js/clearTimeout timeout-id)
                       (js/console.error "Ollama Evaluation Error:" error)
                       (let [err-msg (if (= (.-name error) "AbortError")
                                       "The Consilium took too long to judge (Request timed out after 60 seconds)."
                                       (str "Ollama API Error: " (.-message error) ". Is the server running?"))]
                         (rf/dispatch (conj on-failure err-msg)))))))))))
