(ns commandsearchdemo.core
  (:require [devtools.core :as devtools]
            [reagent.core :as r]))

;; -- Debugging aids ----------------------------------------------------------
(devtools/install!)       ;; we love https://github.com/binaryage/cljs-devtools
(enable-console-print!)   ;; so that println writes to `console.log`

(defn app []
    [:p "this is a test"])

(defn ^:export main []
    (r/render [app] (js/document.getElementById "app")))
