(ns commandsearchdemo.core
  (:require [clojure.string :as string]
            [reagent.core :as r]
            [commandsearchdemo.quake-map :as quake-map]))

(defonce query (r/atom ""))
(defonce results (r/atom []))
(defonce selected-result (r/atom nil))
(defonce show-help (r/atom false))

(defn update-results [query]
  (-> (str "/search/" (js/btoa query))
      (js/fetch)
      (.then #(.json %))
      (.then #(reset! results %))))

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

(defn build-quake [data]
  [:li { :key (.-_id data)
         :on-click #(reset! selected-result data) }
    [:div.strength (.-eq_primary data)]
    [:div.country (.-country data)]
    [:div.location (.-location_name data)]
    [:div.date (.-date data)]])


(defn build-help-example [text]
  [:div.example { :on-click #(do (reset! query text)
                                 (update-results @query)) }
    text])

(def help-section
  [:div.help-section
    [:h4 "Help"]
    [:div.help-subheader "The search allows for specification, comparison, quotation, and logic (AND, OR, NOT)."
                         [:br]
                         "Here are some clickable examples:"]
    (build-help-example "country:Italy")
    (build-help-example "\"SOUTH ISLAND\"")
    (build-help-example "strength>8.6")
    (build-help-example "-strength:0")
    (build-help-example "sea|ocean")
    (build-help-example "100_years_ago<date<90_years_ago")
    (build-help-example "country:'UK' -territory")
    (build-help-example "morocco -spain")
    [:div.help-footer "Click on an earthquake in the list or on the map to select it."
                      [:br]
                      "Attributes in the selected quake are also clickable."]])

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
      [:div.help-button { :on-click #(swap! show-help not) }
        "?"]
      [:ul (if (empty? @results)
             [:li.empty-message "No earthquakes found."]
             (map build-quake @results))]]
    [:div.right
      (when @show-help help-section)
      (quake-map/create @results selected-result)
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
