(set-env!
 :resource-paths  #{"cljs/src"}
 :dependencies '[[org.clojure/clojure         "1.10.1"]
                 [org.clojure/clojurescript   "1.10.764"]
                 [adzerk/boot-cljs            "2.1.5"    :scope "test"]
                 [adzerk/boot-cljs-repl       "0.4.0"    :scope "test"]
                 [adzerk/boot-reload          "0.6.0"    :scope "test"]
                 [pandeiro/boot-http          "0.8.3"    :scope "test"]
                 [crisptrutski/boot-cljs-test "0.3.4"    :scope "test"]
                 [nrepl                       "0.4.5"    :scope "test"]
                 [weasel                      "0.7.0"    :scope "test"]
                 [cider/piggieback            "0.3.10"   :scope "test"]
                 ; [binaryage/devtools          "0.9.10"   :scope "test"]
                 [reagent                     "1.0.0"]])

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
