(ns cicero.events
  (:require
   [re-frame.core :as rf]
   [cicero.db :as db]
   [cicero.ai :as ai]))

(rf/reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 :set-scene
 (fn [db [_ scene]]
   (assoc db :scene scene)))

(rf/reg-event-db
 :update-player-text
 (fn [db [_ text]]
   (assoc db :player-text text)))

(rf/reg-event-db
 :next-level
 (fn [db _]
   (let [next-lvl (inc (:level db))]
     (if (> next-lvl 4)
       (-> db
           (assoc :scene :main-menu)
           (assoc :level 1)
           (assoc :cicero-text (get db/levels 1))
           (assoc :player-text "")
           (assoc :ai-result nil)
           (assoc :ai-evaluating? false))
       (-> db
           (assoc :level next-lvl)
           (assoc :cicero-text (get db/levels next-lvl))
           (assoc :player-text "")
           (assoc :ai-result nil)
           (assoc :ai-evaluating? false))))))

(rf/reg-event-fx
 :submit-speech
 (fn [{:keys [db]} _]
   (if (empty? (:player-text db))
     {:db db}
     {:db (assoc db :ai-evaluating? true :ai-result nil)
      :dispatch [:fetch-ai-evaluation (:player-text db) (:cicero-text db)]})))

(rf/reg-event-fx
 :fetch-ai-evaluation
 (fn [_ [_ player-text cicero-text]]
   {:ai/evaluate {:player-text player-text
                  :cicero-text cicero-text
                  :on-success [:ai-evaluation-success]
                  :on-failure [:ai-evaluation-failure]}}))

(rf/reg-event-db
 :ai-evaluation-success
 (fn [db [_ result]]
   (let [score (get result "score" 0)
         explanation (get result "explanation" "No explanation provided.")
         win? (> score 50)]
     (-> db
         (assoc :ai-evaluating? false)
         (assoc :ai-result {:score score :explanation explanation})
         (update :score + score)
         (update :k-marks (if win? identity inc))))))

(rf/reg-event-db
 :ai-evaluation-failure
 (fn [db [_ error]]
   (-> db
       (assoc :ai-evaluating? false)
       (assoc :ai-result {:score 0 :explanation (str "AI Error: " error)})
       (update :k-marks inc))))
