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
            "< o    >"
            "<      >"
            "<      >"
            "<  o   >"
            "<      >"
            "<      >"
            "< o  o >"
            "v++++++%"])

(def symbols
  { \^ #(blit! %1 %2 0 0 64 64 %3 %4)
    \- #(blit! %1 %2 64 0 64 64 %3 %4)
    \+ #(blit! %1 %2 64 128 64 64 %3 %4)
    \< #(blit! %1 %2 0 64 64 64 %3 %4)
    \> #(blit! %1 %2 128 64 64 64 %3 %4)
    \  #(blit! %1 %2 64 64 64 64 %3 %4)
    \% #(blit! %1 %2 128 128 64 64 %3 %4)
    \v #(blit! %1 %2 0 128 64 64 %3 %4)
    \$ #(blit! %1 %2 128 0 64 64 %3 %4)

    \o (fn [ctx img x y]
         (blit! ctx img 64 64 64 64 x y)
         (blit! ctx img 128 576 64 64 (- x 8) (- y 16))
         (blit! ctx img 192 576 64 64 (- x 20) (- y 10))
         (blit! ctx img 0 576 64 64 x y)
         (blit! ctx img 64 576 64 64 (+ 16 x) y))
   })



(defn rect!
  "Draw a rect to the given context"
  [ctx x y w h]
  (.rect ctx x y w h)
  (.stroke ctx))

(defn clear! [ctx]
  (let [w 800
        h 600]
    (set! (.-strokeColor ctx) "#ffffff")
    (set! (.-fillStyle ctx) "#ffffff")
    (.fillRect ctx 0 0 w h)))

(defn game [state ctx]
  (clear! ctx)
  (doseq [[line row] (map list level (range))
          [c i] (map list line (range))]
      (let [f (symbols c)]
        (f ctx image (* 64 i) (* 64 row)))))

(defn get-context [el]
  (.getContext el "2d"))

(def game-state (atom {}))

(defn looper [f]
  (f)
  (.requestAnimationFrame js/window (partial looper f)))

(looper (fn []
          (game game-state (get-context (.getElementById js/document "board")))))
