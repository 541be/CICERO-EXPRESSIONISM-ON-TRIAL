(ns cicero.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [cicero.subs :as subs]
   [cicero.events :as events]))

(defn main-menu []
  [:div.scene.main-menu
   [:h1.title "CICERO: EXPRESSIONISM ON TRIAL"]
   [:button.menu-btn {:on-click #(rf/dispatch [:start-game])} "Verhandlung beginnen"]
   [:button.menu-btn {:on-click #(rf/dispatch [:set-scene :encyclopedia])} "Expressionismus Lexikon"]
   [:button.menu-btn {:on-click #(rf/dispatch [:set-scene :settings])} "Einstellungen"]])

(defn trial-scene []
  (let [cicero-text @(rf/subscribe [:cicero-text])
        player-text @(rf/subscribe [:player-text])
        ai-evaluating? @(rf/subscribe [:ai-evaluating?])
        ai-result @(rf/subscribe [:ai-result])
        k-marks @(rf/subscribe [:k-marks])]
    [:div.scene.trial-scene
     ;; K-marks punishment overlay
     (for [i (range k-marks)]
       ^{:key i}
       [:div.k-mark {:style {:top (str (+ 10 (* i 15)) "%")
                             :left (str (+ 10 (* i 25)) "%")}}
        "K"])
        
     (when ai-result
       [:div.judge-panel.active 
        [:div.score-display (str (:score ai-result) "/100")]
        [:p (:explanation ai-result)] 
        [:button.submit-btn {:on-click #(rf/dispatch [:next-level])} "Nächster Verhandlungstag"]
        
[:button.submit-btn {:on-click #(rf/dispatch [:quit-game])} "Aufgeben"]])
     
     [:div.character-panel
      [:div.speech-bubble cicero-text]
      [:img.cicero-img {:src "images/IMG_5657.PNG" :alt "Cicero"}]]
     
     [:div.player-panel
      [:textarea.speech-input
       {:value player-text
        :on-change #(rf/dispatch [:update-player-text (-> % .-target .-value)])
        :placeholder "Schreibe dein expressionistisches Plädoyer"
        :disabled ai-evaluating?}]
      [:button.submit-btn
       {:on-click #(rf/dispatch [:submit-speech])
        :disabled ai-evaluating?}
       (if ai-evaluating?
         [:span.loading-text "Die Richter beraten sich..."]
         "Plädoyer halten")]]]))

(defn encyclopedia-scene []
  [:div.scene.encyclopedia-scene
   [:h1 "Expressionismus Lexikon"]
   [:h2 "Lexikon – So schlägst du Cicero!"]
   [:p {:style {:text-align "center"}} "Willkommen, Anwalt! Dein Ziel ist es, einen Text zu schreiben, der expressionistischer ist als Ciceros Originalrede. Je besser du Gefühle, Bilder und Dramatik einsetzt, desto eher überzeugt die KI-Richter deine Version. Aber Vorsicht: Cicero war ein Meister der Rhetorik! In seiner ersten großen Gerichtsrede, Pro Sexto Roscio Amerino (80 v. Chr.), verteidigte er Sextus Roscius gegen den Vorwurf des Vatermordes. Dabei griff er die mächtigen Gegner seines Mandanten mutig an und versuchte, die Richter mit einer besonders eindringlichen Sprache für sich zu gewinnen."]
   [:h2 "So schreibst du expressionistisch"]
   [:h4 "Gefühle zuerst"]
   [:p {:style {:text-align "center"}}"Schreibe nicht nüchtern. Zeige Angst, Wut, Verzweiflung oder Hoffnung so, dass der Leser sie spürt. Die Gefühle sollen stärker wirken als die eigentliche Handlung und den gesamten Text bestimmen."]
   [:h4 "Denk in Bildern"]
   [:p {:style {:text-align "center"}}"Verwandle einfache Aussagen in starke Bilder. Lass Städte schreien, Himmel brennen oder Schatten leben. Je ungewöhnlicher und eindrucksvoller deine Bilder sind, desto ausdrucksstärker wirkt dein Text."]
   [:h4 "Übertreibe!"]
   [:p "Ein Verbrechen ist nicht einfach schlimm – es zerreißt die Welt. Im Expressionismus ist Übertreibung ausdrücklich erlaubt. Scheue dich also nicht davor, Ereignisse größer, bedrohlicher oder dramatischer erscheinen zu lassen, als sie tatsächlich sind."]
   [:h4 "Kurz und heftig"]
   [:p {:style {:text-align "center"}}"Kurze Sätze wirken oft stärker als lange Erklärungen. Ein schneller Rhythmus vermittelt Hektik, Angst oder Wut und zieht den Leser mitten ins Geschehen."]
   [:h4 "Wörter mit Wucht"]
   [:p "Benutze Wörter wie Blut, Feuer, Rauch, Schrei, Finsternis, Sturm, Asche oder Zerfall. Solche Begriffe erzeugen sofort eine düstere Atmosphäre und lassen den Text kraftvoller wirken."]
   [:h4 "Lass die Welt leben"]
   [:p {:style {:text-align "center"}}"Nicht nur Menschen handeln. Auch Häuser, Straßen oder die Natur dürfen fühlen und reagieren. Dadurch wird die Umgebung zum Spiegel der Gefühle und verstärkt die Wirkung deiner Sprache."]
   [:h4 "Zeig das Innere"]
   [:p {:style {:text-align "center"}}"Beschreibe nicht nur, was passiert – zeige, wie sich die Welt für die Figur anfühlt. Die äußere Wirklichkeit darf dabei verzerrt oder sogar unwirklich erscheinen, wenn dadurch die Emotionen deutlicher werden."]
   [:h4 "Typische Themen"]
   [:p {:style {:text-align "center"}}"Krieg, Angst, Großstadt, Tod, Einsamkeit oder der Untergang der Welt sind klassische Themen des Expressionismus. Häufig steht dabei ein einzelner Mensch einer bedrohlichen oder zerfallenden Welt gegenüber."]
   [:h4 "Cicero – ein Expressionist?"]
   [:p {:style {:text-align "center"}}"Obwohl Cicero mehr als 1900 Jahre vor dem Expressionismus lebte, weisen besonders seine frühen Reden wie Pro Sexto Roscio Amerino einzelne Merkmale auf, die heute expressionistisch wirken können. Um die Richter emotional zu überzeugen, schildert er Verbrechen und politische Zustände oft äußerst drastisch und nutzt eindrucksvolle Bilder sowie starke Übertreibungen. Seine Sprache bleibt jedoch grundsätzlich logisch aufgebaut und dient der rhetorischen Überzeugung, nicht dem Ausdruck einer inneren Gefühlswelt. Deshalb war Cicero kein Expressionist – seine Reden lassen sich aber aus heutiger Sicht an einigen Stellen mit expressionistischen Stilmitteln vergleichen."]
   [:button.menu-btn {:on-click #(rf/dispatch [:set-scene :main-menu])} "Zurück zum Hauptmenü"]])

(defn settings-scene []
  (r/with-let [local-backend (r/atom @(rf/subscribe [:api-backend]))
               local-key (r/atom @(rf/subscribe [:gemini-api-key]))]
    [:div.scene.settings-scene
     [:h1 "Settings & AI Configuration"]
     [:div.settings-form
      [:label "AI Backend: "]
      [:select.settings-input {:value (name @local-backend)
                               :on-change #(reset! local-backend (keyword (-> % .-target .-value)))}
       [:option {:value "ollama"} "Local Ollama (gemma4:26b)"]
       [:option {:value "gemini"} "Gemini (3.1 Flash-Lite)"]]
      
      (when (= @local-backend :gemini)
        [:div.gemini-config
         [:label "Gemini API Key: "]
         [:input.settings-input {:type "password"
                                 :value @local-key
                                 :on-change #(reset! local-key (-> % .-target .-value))
                                 :placeholder "AIzaSy..."}]
         [:p.settings-note "Your key is saved locally in your browser and never sent to our servers."]])]
         
     [:div.settings-actions
      [:button.submit-btn {:on-click #(do 
                                        (rf/dispatch [:save-settings @local-backend @local-key])
                                        (rf/dispatch [:set-scene :main-menu]))} "Speichern und zurück"]
      [:button.menu-btn {:on-click #(rf/dispatch [:set-scene :main-menu])} "Abbrechen"]]]))

(defn main-panel []
  (let [scene @(rf/subscribe [:scene])]
    [:div#app-container
     (case scene
       :main-menu [main-menu]
       :trial [trial-scene]
       :encyclopedia [encyclopedia-scene]
       :settings [settings-scene]
       [main-menu])]))
