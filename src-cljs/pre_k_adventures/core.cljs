(ns pre-k-adventures.core
  (:require [pre-k-adventures.background :refer [background]]
            [pre-k-adventures.props :refer [shrubbery]]

            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.match])
  (:require-macros [cljs.core.match.macros :refer [match]]))

(defn game [{:keys [size-x size-y]} owner]
  (reify
    om/IRender
    (render [this]
      (dom/div nil
               (om/build background {:size-x size-x :size-y size-y})
               (apply dom/div nil
                      (om/build-all shrubbery (for [a (range 1 10)]
                                                {:type (if (= (mod a 2) 0) :shrub :tree)
                                                 :color (if (= (mod a 3) 0) :dark-green :brown)
                                                 :large true
                                                 :x (rand-int size-x)
                                                 :y (rand-int size-y)})))))))


(om/root game {:size-x 15 :size-y 15 }
         {:target (. js/document (getElementById "board"))})
