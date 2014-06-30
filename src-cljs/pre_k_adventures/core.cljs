(ns pre-k-adventures.core
  (:require [pre-k-adventures.background :refer [background]]
            [pre-k-adventures.props :refer [shrubbery shrub-cluster]]
            [pre-k-adventures.path :refer [path]]

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
               (om/build shrub-cluster {:x 5 :y 5 :width 3 :height 1})
               (om/build shrub-cluster {:x 3 :y 8 :width 3 :height 1})))))


(om/root game {:size-x 15 :size-y 15 }
         {:target (. js/document (getElementById "board"))})
