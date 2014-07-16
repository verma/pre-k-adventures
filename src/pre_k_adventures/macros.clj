(ns pre-k-adventures.macros)

(defmacro offset-rand [x by]
  `(pre-k-adventures.util/offset-by ~x ~(- by (rand-int (* 2 by)))))
