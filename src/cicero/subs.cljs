(ns cicero.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 :scene
 (fn [db]
   (:scene db)))

(rf/reg-sub
 :level
 (fn [db]
   (:level db)))

(rf/reg-sub
 :score
 (fn [db]
   (:score db)))

(rf/reg-sub
 :k-marks
 (fn [db]
   (:k-marks db)))

(rf/reg-sub
 :cicero-text
 (fn [db]
   (:cicero-text db)))

(rf/reg-sub
 :player-text
 (fn [db]
   (:player-text db)))

(rf/reg-sub
 :ai-evaluating?
 (fn [db]
   (:ai-evaluating? db)))

(rf/reg-sub
 :ai-result
 (fn [db]
   (:ai-result db)))

(rf/reg-sub
 :api-backend
 (fn [db]
   (:api-backend db)))

(rf/reg-sub
 :gemini-api-key
 (fn [db]
   (:gemini-api-key db)))
