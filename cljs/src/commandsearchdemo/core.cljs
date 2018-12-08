(ns commandsearchdemo.core
  (:require [reagent.core :as r]))

(enable-console-print!)

(def query (r/atom ""))
(def results (r/atom []))

(defn fetch [url]
  (-> (js/fetch url)
      (.then #(.json %))))

(defn update-results []
  ; TODO: sanitize this
  ; TODO:
  (-> (str "/search/" @query)
      (fetch)
      (.then #(reset! results %))))

(defn build-quake [data]
  [:p { :key (.-_id data) }
    (.-eq_primary data)
    " "
    (.-location_name data)
    " "
    " "
    (.-date data)
    ])

(defn app []
    [:div [:p "Search Earthquakes"]
          [:input { :value @query
                    :on-change #(do (reset! query  (-> % .-target .-value))
                                    (update-results))}]
          (map build-quake @results)])

(defn ^:export main []
    (r/render [app] (js/document.getElementById "app")))


; TODO: add disclaimer about data and dates being defaulted and all
