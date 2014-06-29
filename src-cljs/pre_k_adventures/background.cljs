(ns pre-k-adventures.background
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.match])
  (:require-macros [cljs.core.match.macros :refer [match]]))

(defn- border-offset [x y size-x size-y]
  (let [sx (* 64 (dec size-x))
        sy (* 64 (dec size-y))]
    (match [x y]
           [0 0] "0px 0px"
           [sx sy] "-128px -128px"
           [0 sy] "-128px 0px"
           [sx 0] "0px -128px"
           [sx _] "-64px -128px"
           [_ sy] "-128px -64px"
           [0 _] "-64px 0px"
           [_ 0] "0px -64px"
           :else "-64px -64px")))

(defn- background-tile [{:keys [pos-x pos-y size-x size-y]} owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "tile"
                    :style #js {:position "absolute"
                                :top pos-x
                                :left pos-y
                                :backgroundImage "url(img/tileset.png)"
                                :backgroundPosition (border-offset pos-x pos-y size-x size-y)
                                :width "64px"
                                :zIndex "-5000"
                                :height "64px"}}
               ""))))

(defn background
  "Render the background of the given size"
  [{:keys [size-x size-y]} owner]
  (reify
    om/IRender
    (render [this]
      (apply dom/div nil
               (om/build-all background-tile (for [x (range 0 size-x)
                                                   y (range 0 size-y)]
                                               {:pos-x (* 64 x)
                                                :pos-y (* 64 y)
                                                :size-x size-x
                                                :size-y size-y}))))))
