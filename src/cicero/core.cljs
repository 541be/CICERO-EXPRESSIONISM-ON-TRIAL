(ns cicero.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as rf]
   [cicero.events :as events]
   [cicero.subs :as subs]
   [cicero.views :as views]))

(defn dev-setup []
  (when ^boolean js/goog.DEBUG
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (rf/clear-subscription-cache!)
  (let [root-el (.getElementById js/document "app")]
    (rdom/render [views/main-panel] root-el)))

(defn init []
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
