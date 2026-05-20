(ns cicero.views
  (:require
   [re-frame.core :as rf]
   [reagent.core :as r]
   [cicero.subs :as subs]
   [cicero.events :as events]))

(defn main-menu []
  [:div.scene.main-menu
   [:h1.title "CICERO: EXPRESSIONISM ON TRIAL"]
   [:button.menu-btn {:on-click #(rf/dispatch [:set-scene :trial])} "Start Trial"]
   [:button.menu-btn {:on-click #(rf/dispatch [:set-scene :encyclopedia])} "Expressionism Encyclopedia"]])

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
                             :left (str (+ 10 (* i 25)) "%")
                             :transform (str "rotate(" (- (rand-int 60) 30) "deg)")}}
        "K"])
        
     (when ai-result
       [:div.judge-panel.active
        [:div.score-display (str (:score ai-result) "/100")]
        [:p (:explanation ai-result)]
        [:button.submit-btn {:on-click #(rf/dispatch [:next-level])} "Next Level / Return"]])
        
     [:div.character-panel
      [:div.speech-bubble cicero-text]
      [:img.cicero-img {:src "/images/IMG_5657.PNG" :alt "Cicero"}]]
      
     [:div.player-panel
      [:textarea.speech-input
       {:value player-text
        :on-change #(rf/dispatch [:update-player-text (-> % .-target .-value)])
        :placeholder "Write your expressionistic retort..."
        :disabled ai-evaluating?}]
      [:button.submit-btn
       {:on-click #(rf/dispatch [:submit-speech])
        :disabled ai-evaluating?}
       (if ai-evaluating?
         [:span.loading-text "THE CONSILIUM IS JUDGING..."]
         "SUBMIT SPEECH")]]]))

(defn encyclopedia-scene []
  [:div.scene.encyclopedia-scene
   [:h1 "Expressionism Encyclopedia"]
   [:p "Unlock entries explaining metaphor, fragmentation, apocalypse imagery..."]
   [:button.menu-btn {:on-click #(rf/dispatch [:set-scene :main-menu])} "Back"]])

(defn main-panel []
  (let [scene @(rf/subscribe [:scene])]
    [:div#app-container
     (case scene
       :main-menu [main-menu]
       :trial [trial-scene]
       :encyclopedia [encyclopedia-scene]
       [main-menu])]))
