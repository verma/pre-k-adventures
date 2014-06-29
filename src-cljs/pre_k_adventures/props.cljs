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
                                :top (* x 64)
                                :left (* y 64)
                                :backgroundImage "url(img/tileset.png)"
                                :backgroundPosition (shrub-offset type color large)
                                :zIndex "-4000"
                                :width "64px"
                                :height (if (= type :tree) "128px" "64px")}}
               " "))))

