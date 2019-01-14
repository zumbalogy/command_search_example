(ns commandsearchdemo.quake-map)

(defn create [quakes selected]
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
          zoom (min 20 (/ 300 max-dif))
          transX (/ (* 50 mid-long) -180)
          transY (/ (* 50 mid-lat) 90)
          transform (when (< 1.4 zoom) (str "scale(" zoom ") translate(" transX "%, " transY "%)"))
          build-quake-svg (fn [data]
            [:circle { :key (.-id data)
                       :cx (+ 180 (.-longitude data))
                       :cy (- 90 (.-latitude data))
                       :r (if (= data @selected) (/ 3 zoom) (/ 2 zoom))
                       :id (when (= data @selected) "selected")
                       :fill (if (= data @selected) "#f118" "#1278") ; TODO: the trasparency should ajust depending on how many results. (and be set outside the loop)
                       :on-click #(reset! selected data)}])] ; TODO: selected quake stuff should happen after this stuff for performance reasons.
      [:div.map-wrapper
        [:img { :src "/blank_map2.svg" :style {  :transform transform } }]
        [:svg {:x 0 :y 0 :viewBox [0 0 360 180] :style { :transform transform } }
          (doall (map build-quake-svg quakes))
          [:use { :xlinkHref "#selected" }]]])))
