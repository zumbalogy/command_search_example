(ns commandsearchdemo.quake-map)

(defn create [quakes selected]
  (if (empty? quakes)
    [:div.map-wrapper [:img { :src "/blank_map2.svg" }]]
    (let [[min-lat max-lat min-long max-long] ((aget js/window "longLatFinder") quakes)

          mid-lat (/ (+ min-lat max-lat) 2)
          dif-lat (- max-lat min-lat)

          mid-long (/ (+ min-long max-long) 2)
          dif-long (- max-long min-long)

          max-dif (max 1 dif-long (* 2 dif-lat))
          zoom (min 20 (/ 300 max-dif))
          transX (/ (* 50 mid-long) -180)
          transY (/ (* 50 mid-lat) 90)
          transform (when (< 1.4 zoom) (str "scale(" zoom ") translate(" transX "%, " transY "%)"))
          radius (/ 2 zoom)

          build-quake-svg (fn [data]
            [:circle { :key (.-id data)
                       :cx (+ 180 (.-longitude data))
                       :cy (- 90 (.-latitude data))
                       :r radius
                       :fill "#1277"
                       :on-click #(reset! selected data)}])]
      [:div.map-wrapper
        [:img { :src "/blank_map2.svg" :style {  :transform transform } }]
        [:svg {:x 0 :y 0 :viewBox [0 0 360 180] :style { :transform transform } }
          (doall (map build-quake-svg quakes))
          (when @selected
            [:circle { :key "selected"
                       :cx (+ 180 (.-longitude @selected))
                       :cy (- 90 (.-latitude @selected))
                       :r (/ 4 zoom)
                       :fill "#f11" }])]])))
