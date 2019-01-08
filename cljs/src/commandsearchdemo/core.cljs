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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn quake-map [quakes]
  (if (empty? quakes)
    [:div.map-wrapper [:img { :src "/blank_map2.svg" }]]
    (let [max-lat (apply max (map #(.-latitude %) quakes))
          min-lat (apply min (map #(.-latitude %) quakes))
          mid-lat (/ (+ min-lat max-lat) 2)
          dif-lat (- max-lat min-lat)

          max-long (apply max (map #(.-longitude %) quakes))
          min-long (apply min (map #(.-longitude %) quakes))
          mid-long (/ (+ min-long max-long) 2)
          dif-long (- max-long min-long)

          max-dif (max 1 dif-long (* 2 dif-lat))
          zoom (- 9.3 (/ (Math/log max-dif) (Math/log 2)))
          transX (/ (* 50 mid-long) -180)
          transY (/ (* 50 mid-lat) 90)
          transform (when (< 1.5 zoom) (str "scale(" zoom ") translate(" transX "%, " transY "%)"))
          build-quake-svg (fn [data]
            [:circle { :key (.-id data)
                       :cx (+ 180 (.-longitude data))
                       :cy (- 90 (.-latitude data))
                       :r (if (= data @selected-result) (/ 3 zoom) (/ 2 zoom))
                       :id (when (= data @selected-result) "selected")
                       :fill (if (= data @selected-result) "#f11" "#235")
                       :on-click #(reset! selected-result data)}])]
      [:div.map-wrapper
        [:img { :src "/blank_map2.svg" :style {  :transform transform } }]
        [:svg {:x 0 :y 0 :viewBox [0 0 360 180] :style { :transform transform } }
          (doall (map build-quake-svg quakes))
          [:use { :xlinkHref "#selected" }]]])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn capitalize-words [s]
  (string/join (map string/capitalize (string/split s #"\b"))))

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
        (capitalize-words (str name ": " (quake attr)))])))

(defn selected-quake [quake]
  (when quake
    (let [q (js->clj quake)]
      [:div.selected-quake
        [:h4 "Selected Quake"]
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

(defn build-quake [data]
  [:li { :key (.-_id data)
         :on-click #(reset! selected-result data) }
    [:div.strength (.-eq_primary data)]
    [:div.country (.-country data)]
    [:div.location (.-location_name data)]
    [:div.date (.-date data)]])

(defn center []
  [:div.center
    [:div.left
      [:input { :value @query
                :placeholder "Search..."
                :on-change #(do (reset! query (-> % .-target .-value))
                                (update-results @query))}]
        [:ul (if (empty? @results)
               [:li.empty-message "No earthquakes found."]
               (map build-quake @results))]]
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
