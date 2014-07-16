(defproject pre-k-adventures "0.1.0-SNAPSHOT"
  :description "A simple game for Pre-Ks to learn programming"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojurescript "0.0-2234"]
                 [org.clojure/core.match "0.2.1"]
                 [om "0.6.4"]]
  :plugins [[lein-cljsbuild "1.0.3"]]
  :cljsbuild {
              :builds [{
                        ; The path to the top-level ClojureScript source directory:
                        :source-paths ["src"]
                        ; The standard ClojureScript compiler options:
                        ; (See the ClojureScript compiler documentation for details.)
                        :compiler {
                                   :output-to "js/main.js"  ; default: target/cljsbuild-main.js
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
