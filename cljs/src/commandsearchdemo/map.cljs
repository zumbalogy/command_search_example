(defn build-quake-svg [data]
  [:circle { :key (.-id data)
             :cx (+ 180 (.-longitude data))
             :cy (- 180 (+ 90 (.-latitude data)))
             :r 2
             :fill "#f22"
             :on-click #(reset! selected-result data)}])

(defn build-selected-quake-svg [data]
  (when data
   [:circle { :key (.-id data)
              :cx (+ 180 (.-longitude data))
              :cy (- 180 (+ 90 (.-latitude data)))
              :r 3
              :fill "#3f3"}]))

(defn quake-map [quakes]
  [:div.map-wrapper
    [:img { :src "/blank_map.svg" }]
    [:svg {:x 0 :y 0 :width 360 :height 180}
      (map build-quake-svg quakes)
      (build-selected-quake-svg @selected-result)]])
