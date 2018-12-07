(ns commandsearchdemo.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(defn app []
    [:div [:p "Search Earthquakes"]
          [:input]])

(defn ^:export main []
    (r/render [app] (js/document.getElementById "app")))
