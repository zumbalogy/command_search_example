(ns commandsearchdemo.core
  (:require [clojure.string :as string]
            [reagent.core :as r]
            [commandsearchdemo.quake-map :as quake-map]))

(declare update-results)

(defonce query (r/atom ""))
(defonce results (r/atom []))
(defonce scroll-offset (r/atom 0))
(defonce selected-result (r/atom nil))
(defonce show-help (r/atom false))

(def fast-quake-filter (aget js/window "fastQuakeFilter"))
(def encode (aget js/window "b64EncodeUnicode"))
(def decode (aget js/window "b64DecodeUnicode"))

(defonce hash-change-listener
  (aset js/window "onhashchange" (fn []
    (update-results (decode (subs js/window.location.hash 2))))))

(when-not (exists? all-quakes)
  (-> (js/fetch "quake_export.json")
      (.then #(.json %))
      (.then #(defonce all-quakes %))
      (.then #(reset! results all-quakes))
      (.then #(update-results (decode (subs js/window.location.hash 2))))))

(defn pluck-from-all-quakes [ids]
  (fast-quake-filter ids all-quakes))

(defn update-results [input]
  (let [prev (.trim @query)
        clean (.trim input)
        encoded (encode clean)]
    (reset! query input)
    (when (not= clean prev)
      (set! js/window.location (str "#/" encoded))
      (if (= clean "")
        (reset! results all-quakes)
        (-> (str "/search/" encoded)
            (js/fetch)
            (.then #(.json %))
            (.then #(reset! results (pluck-from-all-quakes %))))))))

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
      [:div { :on-click #(update-results (str name ":" (format-query-end (quake attr)))) }
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
  [:div.example { :on-click #(update-results text) }
    text])

(def help-section
  [:div.help-section
    [:h4 "Help"]
    [:div.help-subheader "The search field allows for specification of fields (:), comparisons (< > <= >=), negation (-), a logical OR (|), quotation, and grouping via parentheses. Further details can be found " [:a {:href "https://github.com/zumbalogy/command_search#syntax" :target "_blank" } "here"] "."
                         [:br]
                         "Here are some clickable examples:"]
    (build-help-example "country:Italy")
    (build-help-example "\"south island\"")
    (build-help-example "size>8.6")
    (build-help-example "-size:0")
    (build-help-example "sea|ocean")
    (build-help-example "100_years_ago<date<90_years_ago")
    (build-help-example "country:'uk' -territory")
    (build-help-example "morocco -spain")
    [:div.help-footer "Click on an earthquake in the list or on the map to select it."
                      [:br]
                      "Attributes in the selected quake are also clickable."]])

(defn app []
  [:div.center
    [:div.left
      [:input { :value @query
                :placeholder "Search..."
                :on-change #(update-results (-> % .-target .-value)) }]
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
