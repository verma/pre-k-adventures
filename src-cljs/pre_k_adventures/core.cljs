(ns pre-k-adventures.core
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [cljs.core.match])
  (:require-macros [cljs.core.match.macros :refer [match]]))

(def image 
  (let [img (js/Image.)]
    (aset img "src" "img/tileset.png")
    img))

(defn blit!
  "Blit an image into a context"
  ([ctx img dx dy]
   (.drawImage ctx img dx dy))

  ([ctx img dx dy dw dh]
   (.drawImage ctx img dx dy dw dh))

  ([ctx img sx sy sw sh dx dy]
   (.drawImage ctx img sx sy sw sh dx dy sw sh))

  ([ctx img sx sy sw sh dx dy dw dh]
   (.drawImage ctx img sx sy sw sh dx dy dw dh)))

(def level ["^------$"
            "<      >"
            "<      >"
            "<      >"
            "<      >"
            "<      >"
            "<      >"
            "<      >"
            "v++++++%"])

(def symbols
  { \^ '(0 0 64 64)
    \- '(64 0 64 64)
    \+ '(64 128 64 64)
    \< '(0 64 64 64)
    \> '(128 64 64 64)
    \  '(64 64 64 64)
    \% '(128 128 64 64)
    \v '(0 128 64 64)
    \$ '(128 0 64 64) })



(defn rect!
  "Draw a rect to the given context"
  [ctx x y w h]
  (.rect ctx x y w h)
  (.stroke ctx))

(defn game [state ctx]
  (doseq [[line row] (map list level (range))
          [c i] (map list line (range))]
      (let [[sx sy sw sh] (symbols c)]
        (blit! ctx image sx sy sw sh (* 64 i) (* 64 row)))))

(defn get-context [el]
  (.getContext el "2d"))

(def game-state (atom {}))

(defn looper [f]
  (f)
  (.requestAnimationFrame js/window (partial looper f)))

(looper (fn []
          (game game-state (get-context (.getElementById js/document "board")))))
