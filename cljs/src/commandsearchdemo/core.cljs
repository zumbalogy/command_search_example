(ns commandsearchdemo.core
  (:require [clojure.string :as string]
            [reagent.core :as r]
            [commandsearchdemo.quake-map :as quake-map]))

(defonce all-quakes (aget js/window "allQuakes"))

(defonce query (r/atom ""))
(defonce results (r/atom all-quakes))
(defonce selected-result (r/atom nil))
(defonce show-help (r/atom false))

(defn pluck-from-all-quakes [ids]
  ((aget js/window "fastQuakeFilter") ids all-quakes))

(defn update-results [query]
  (if (= query "")
    (reset! results all-quakes)
    (-> (str "/search/" (js/btoa query))
        (js/fetch)
        (.then #(.json %))
        (.then #(reset! results (pluck-from-all-quakes %))))))

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
        (selected-quake-section q "houses_damaged")])))

(defn build-quake [data]
  [:li { :key (aget data "_id")
         :on-click #(reset! selected-result data) ; this is potentally slow performance
       }
    [:div.strength (aget data "eq_primary")]
    [:div.country (aget data "country")]
    [:div.location (aget data "location_name")]
    [:div.date (aget data "date")]])


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

(defn app []
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

(defn ^:export main []
    (r/render [app] (js/document.getElementById "splash-render-hook")))
