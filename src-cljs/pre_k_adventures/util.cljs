(ns pre-k-adventures.utils)

(defn tile-offset [x y]
  (str "-" (* x 64) "px -" (* y 64) "px"))
