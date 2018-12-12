(ns commandsearchdemo.core
  (:require [clojure.string :as string]
            [reagent.core :as r]))

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
    [:td (.-country data)]
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

(defn format-query-end [text]
  (if (string/includes? (str text) " ")
    (str "'" text "'")
    text))

(defn selected-quake-section
  ([quake attr]
    (selected-quake-section quake attr attr))
  ([quake attr name]
    (let [text (str name ": " (quake attr))]
      [:div { :on-click #(do (reset! query (str name ":" (format-query-end (quake attr))))
                             (update-results @query)) }
        (str name ": " (quake attr))])))

(defn selected-quake [quake]
  (when quake
    (let [q (js->clj quake)]
      [:div.selected-quake
        (selected-quake-section q "country")
        (selected-quake-section q "location_name" "location")
        (selected-quake-section q "eq_primary" "strength")
        (selected-quake-section q "intensity")
        (selected-quake-section q "focal_depth")
        (selected-quake-section q "flag_tsunami" "tsunami")
        (selected-quake-section q "latitude" "lat")
        (selected-quake-section q "longitude" "long")
        (selected-quake-section q "date")
        (selected-quake-section q "region_code")
        (selected-quake-section q "houses_destroyed")
        (selected-quake-section q "houses_damaged")
        ; [:div "cljs: " (str (js->clj quake))]
        ; [:div "raw: " (.-raw quake)]
        ])))

(def header
  [:div.header
    [:div.left
      [:h3 "Earthquakes"]]
    [:div.right
      [:a { :href "https://www.ngdc.noaa.gov/nndc/struts/form?t=101650&s=1" :target "_blank" }
        "National Geophysical Data Center / World Data Service (NGDC/WDS): NCEI/WDS Global Significant Earthquake Database. NOAA National Centers for Environmental Information. doi:10.7289/V5TD9V7K"]
      [:p "Disclaimer: This data has been normalized and approximated from the original NCEI/WDS source."]]])

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
      (selected-quake @selected-result)]])

(def footer
  [:div.footer
    [:a.source-code { :href "https://github.com/zumbalogy/command_search_example" :target "_blank"}
      "Source Code"]])

(defn app []
  [:div header (center) footer])

(defn ^:export main []
    (r/render [app] (js/document.getElementById "splash-render-hook"))
    (update-results @query))
