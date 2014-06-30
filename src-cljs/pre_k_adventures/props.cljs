(ns pre-k-adventures.props
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.match])
  (:require-macros [cljs.core.match.macros :refer [match]]))

(defn- shrub-offset [type color large]
  (match [type color large]
         [:tree :green true] "0px -640px"
         [:tree :green false] "-64px -640px"
         [:tree :brown true] "-128px -640px"
         [:tree :brown false] "-192px -640px"
         [:tree :dark-green true] "-256px -640px"
         [:tree :dark-green false] "-320px -640px"
         [:shrub :green true] "0px -576px"
         [:shrub :green false] "-64px -576px"
         [:shrub :brown true] "-128px -576px"
         [:shrub :brown false] "-192px -576px"
         [:shrub :dark-green true] "-256px -576px"
         [:shrub :dark-green false] "-320px -576px"
         :else "0px 0px"))

(defn shrubbery [{:keys [x y type color large]} owner]
  (reify
    om/IRender
    (render [this]
      (.log js/console "shrubbery")
      (dom/div #js {:className "shrub"
                    :style #js {:position "absolute"
                                :top (* y 64)
                                :left (* x 64)
                                :backgroundImage "url(img/tileset.png)"
                                :backgroundPosition (shrub-offset type color large)
                                :zIndex "-4000"
                                :width "64px"
                                :height (if (= type :tree) "128px" "64px")}}
               " "))))


(defn shrub-cluster
  "Generate a cluster of shrubs so that it looks nice"
  [{:keys [x y width height]} owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div nil
             (om/build-all shrubbery (for [_x (range 0 width)
                                           _y (range 0 height)]
                                       {:x (+ x _x) :y (+ y _y)
                                        :type :shrub
                                        :color (rand-nth [:green :dark-green :brown])
                                        :large (rand-nth [true false])})))

      )))

