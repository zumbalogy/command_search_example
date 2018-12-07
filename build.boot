(set-env!
 :resource-paths  #{"cljs/src"}
 :dependencies '[[org.clojure/clojure         "1.9.0"]
                 [org.clojure/clojurescript   "1.10.439"]
                 [adzerk/boot-cljs            "1.7.228-2"      :scope "test"]
                 [adzerk/boot-cljs-repl       "0.3.3"          :scope "test"]
                 [adzerk/boot-reload          "0.6.0"          :scope "test"]
                 [pandeiro/boot-http          "0.8.3"          :scope "test"]
                 [crisptrutski/boot-cljs-test "0.3.5-SNAPSHOT" :scope "test"]
                 [org.clojure/tools.nrepl     "0.2.12"         :scope "test"]
                 [com.cemerick/piggieback     "0.2.1"          :scope "test"]
                 [weasel                      "0.7.0"          :scope "test"]
                 [binaryage/devtools          "0.9.10"]
                 [cljsjs/react                "16.6.0-0"]
                 [cljsjs/react-dom            "16.6.0-0"]
                 [reagent                     "0.8.1"]])

(require
  '[adzerk.boot-cljs             :refer [cljs]]
  '[adzerk.boot-cljs-repl        :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload           :refer [reload]]
  '[crisptrutski.boot-cljs-test  :refer [test-cljs]]
  '[pandeiro.boot-http           :refer [serve]])

(deftask dev []
  (comp (serve :port 5555)
        (watch)
        ; (speak)
        (reload :asset-host "http://localhost:5555"
                :on-jsload 'commandsearchdemo.core/main)
        (cljs-repl)
        (cljs :source-map true
              :optimizations :none)))

(deftask prod []
  (comp (cljs :optimizations :advanced)
        (target :dir #{"app/assets/cljs-build"})))
