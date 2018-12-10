(ns commandsearchdemo.core
  (:require [reagent.core :as r]))

(defonce query (r/atom ""))
(defonce results (r/atom []))
(defonce selected-result (r/atom nil))

(defn update-results [query]
  (-> (str "/search/" (js/btoa query))
      (js/fetch)
      (.then #(.json %))
      (.then #(reset! results %))))

(defn build-quake [data]
  [:tr { :key (.-_id data)
         :on-click #(reset! selected-result data) }
    [:td (.-eq_primary data)]
    [:td (.-location_name data)]
    [:td (.-date data)]])

(defn build-quake-svg [data]
  [:circle { :key (.-id data)
             :cx (+ 180 (.-longitude data))
             :cy (- 180 (+ 90 (.-latitude data)))
             :r 2
             :fill "#f22"
             :width 10
             :height 10
             :on-click #(reset! selected-result data)}])

(defn quake-map [quakes]
  [:div.map-wrapper
    [:img { :src "/blank_map.svg" }]
    [:svg {:x 0 :y 0 :width 360 :height 180}
      (map build-quake-svg quakes)]])

(def header
  [:div.header
    [:div.left
      [:h3 "Earthquakes"]]
    [:div.right
      [:a { :href "https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1" :target "_blank"}
        "National Geophysical Data Center / World Data Service (NGDC/WDS): NCEI/WDS Global Significant Earthquake Database. NOAA National Centers for Environmental Information. doi:10.7289/V5TD9V7K"]
      [:p "Disclaimer: This data has been cleaned in a lossy manner from the original NCEI/WDS source."]]])

(defn center []
  [:div.center
    [:div.left
       [:input { :value @query
                 :placeholder "Search..."
                 :on-change #(do (reset! query (-> % .-target .-value))
                                 (update-results @query))}]
       [:table
         [:tbody (map build-quake @results)]]]
    [:div.right
      (quake-map @results)
      [:p (str (js->clj @selected-result))]]])

(def footer
  [:div.footer
    [:a.source-code { :href "https://github.com/zumbalogy/command_search_example" :target "_blank"}
      "Source Code"]])

(defn app []
  [:div header (center) footer])

(defn ^:export main []
    (r/render [app] (js/document.getElementById "splash-render-hook"))
    (update-results @query))
