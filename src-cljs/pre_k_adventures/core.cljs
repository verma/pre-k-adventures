(ns pre-k-adventures.core
  (:require [cljs.core.match])
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

(defn blit-cell!
  "Blit a cell"
  ([ctx img sx sy dx dy]
   (blit! ctx img (* sx 64) (* sy 64) 64 64 (* dx 64) (* dy 64)))

  ([ctx img sx sy sw sh dx dy]
   (blit! ctx img (* sx 64) (* sy 64) (* sw 64) (* sh 64) (* dx 64) (* dy 64))))

(def level ["^---------$"
            "< o       >"
            "<         >"
            "<         >"
            "<  o  l   >"
            "<         >"
            "<         >"
            "< o  O    >"
            "v+++++++++%"])

(defn offset-by [e by]
  (+ e (/ by 64)))

(def symbols
  { \^ #(blit-cell! %1 %2 0 0 %3 %4)
    \- #(blit-cell! %1 %2 1 0 %3 %4)
    \+ #(blit-cell! %1 %2 1 2 %3 %4)
    \< #(blit-cell! %1 %2 0 1 %3 %4)
    \> #(blit-cell! %1 %2 2 1 %3 %4)
    \  #(blit-cell! %1 %2 1 1 %3 %4)
    \% #(blit-cell! %1 %2 2 2 %3 %4)
    \v #(blit-cell! %1 %2 0 2 %3 %4)
    \$ #(blit-cell! %1 %2 2 0 %3 %4)

    \o (fn [ctx img x y]
         (blit-cell! ctx img 2 9 (offset-by x 8) (offset-by y -16))
         (blit-cell! ctx img 3 9 (offset-by x -20) (offset-by y -10))
         (blit-cell! ctx img 0 9 x y)
         (blit-cell! ctx img 1 9 (offset-by x 16) y))

   \O (fn [ctx img x y]
         (blit-cell! ctx img 3 9 (offset-by x 20) (offset-by y -40))
         (blit-cell! ctx img 0 9 x (offset-by y -16))
         (blit-cell! ctx img 2 9 (offset-by x 8) (offset-by y -8)))

   ;; for multi-tile tiles always draw up, drawing down will be overritten by the next line
   \l (fn [ctx img x y]
        (blit-cell! ctx img 3 10 1 2 (offset-by x -20) (offset-by (- y 1) -4))
        (blit-cell! ctx img 0 10 1 2 x (- y 1))
        (blit-cell! ctx img 1 10 1 2 (offset-by x 16) (offset-by (- y 1) 10)))
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

(defn draw-pass
  ([ctx level f default]
   "Only draw cells for which f returns truthy, for cells where f is not truthy, default is drawn"
   (doseq [[line row] (map list level (range))
           [c i] (map list line (range))
           c (if (f c) c default)]
     (let [fdraw (symbols c)]
       (fdraw ctx image i row))))

  ([ctx level f]
   "Only draw cells for which f returns truthy"
   (draw-pass ctx level f nil)))

(defn game [state ctx]
  (let [bg-tiles #{ \  \+  \< \> \- \^ \$ \v \% }]
    (clear! ctx)
    (draw-pass ctx level bg-tiles \ )
    (draw-pass ctx level (complement bg-tiles))))

(defn get-context [el]
  (.getContext el "2d"))

(def game-state (atom {}))

(defn looper [f]
  (f)
  (.requestAnimationFrame js/window (partial looper f)))

(looper (fn []
          (game game-state (get-context (.getElementById js/document "board")))))
