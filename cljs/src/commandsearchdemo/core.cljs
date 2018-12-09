(ns commandsearchdemo.core
  (:require [reagent.core :as r]))

(def query (r/atom ""))
(def results (r/atom []))

(defn update-results [query]
  (-> (str "/search/" (js/btoa query))
      (js/fetch)
      (.then #(.json %))
      (.then #(reset! results %))))

(defn build-quake [data]
  [:tr { :key (.-_id data) }
    [:td (.-eq_primary data)]
    [:td (.-location_name data)]
    [:td (.-date data)]])

(defn app []
    [:div [:p "Search Earthquakes"]
          [:input { :value @query
                    :on-change #(do (reset! query (-> % .-target .-value))
                                    (update-results @query))}]
          [:p "Disclaimer: This data has been cleaned in a lossy manner. See the full data at"]
          [:a "https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1"]
          [:table
            [:tbody (map build-quake @results)]]])

(defn ^:export main []
    (r/render [app] (js/document.getElementById "app"))
    (update-results @query))


; TODO: link the github
