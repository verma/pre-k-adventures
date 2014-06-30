(ns pre-k-adventures.level
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.match])
  (:require-macros [cljs.core.match.macros :refer [match]]))

(defn level-from-str
  "Load level from string"
  [txt]
  (reify
    om/IRender
    (render [this])))
