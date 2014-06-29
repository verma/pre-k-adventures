(ns pre-k-adventures.path
  (:require [pre-k-adventures.utils :refer [tile-offset]]
            [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.match])
  (:require-macros [cljs.core.match.macros :refer [match]]))

(defn- path-start-tile [dir]
  (match [dir]
         [:north] (tile-offset 6 0)
         [:south] (tile-offset 6 2)
         [:east] (tile-offset 7 1)
         [:west] (tile-offset 5 1)
         :else (tile-offset 0 0)))

(defn path [{:keys [startx starty endx endy]} owner]
  (reify
    om/IRender
    (render [this]
      (dom/div #js {:className "path"
                    :style #js {:position "absolute"
                                :top (* startx 64)
                                :left (* starty 64)
                                :backgroundImage "url(img/tileset.png)"
                                :backgroundPosition (path-start-tile :west)
                                :zIndex "-3500"
                                :width "64px"
                                :height "64px"}}
               " "))))

