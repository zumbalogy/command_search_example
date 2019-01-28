(ns commandsearchdemo.core
  (:require [clojure.string :as string]
            [reagent.core :as r]
            [commandsearchdemo.quake-map :as quake-map]))

(defonce query (r/atom ""))
(defonce results (r/atom []))
(defonce scroll-offset (r/atom 0))
(defonce selected-result (r/atom nil))
(defonce show-help (r/atom false))

(when-not (exists? all-quakes)
  (-> (js/fetch "quake_export.json")
      (.then #(.json %))
      (.then #(defonce all-quakes %))
      (.then #(reset! results all-quakes))
      (.then #(reset! query (js/atob (subs js/window.location.hash 2))))
      (.then #(update-results @query))))

(defn pluck-from-all-quakes [ids]
  ((aget js/window "fastQuakeFilter") ids all-quakes))

(defn update-results [query]
  (let [base64 (js/btoa query)]
    (set! js/window.location (str "#/" base64))
    (if (= query "")
      (reset! results all-quakes)
      (-> (str "/search/" base64)
          (js/fetch)
          (.then #(.json %))
          (.then #(reset! results (pluck-from-all-quakes %)))))))

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
        (selected-quake-section q "location")
        (selected-quake-section q "eq_primary" "size")
        (selected-quake-section q "intensity")
        (selected-quake-section q "focal_depth")
        (selected-quake-section q "tsu" "tsunami")
        (selected-quake-section q "latitude" "lat")
        (selected-quake-section q "longitude" "long")
        (selected-quake-section q "date")
        (selected-quake-section q "region")
        (selected-quake-section q "houses_destroyed")
        (selected-quake-section q "houses_damaged")])))

(defn build-quake [data]
  [:li { :key (aget data "_id")
         :on-click #(reset! selected-result data) }
    [:div.size (aget data "eq_primary")]
    [:div.country (aget data "country")]
    [:div.location (aget data "location")]
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
    (build-help-example "size>8.6")
    (build-help-example "-size:0")
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
      [:ul { :on-scroll #(reset! scroll-offset (-> % .-target .-scrollTop)) }
        (if (empty? @results)
          (if (= "" @query)
              [:li.empty-message "Loading..."]
              [:li.empty-message "No earthquakes found."])
            (concat
              (map build-quake (take 200 @results))
              (when (< 500 @scroll-offset)
                (map build-quake (drop 200 @results)))))]]
    [:div.right
      (when @show-help help-section)
      (quake-map/create @results selected-result)
      (selected-quake @selected-result)]])

(defn ^:export main []
    (r/render [app] (js/document.getElementById "splash-render-hook")))
