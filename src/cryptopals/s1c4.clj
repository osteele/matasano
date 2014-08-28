(ns cryptopals.s1c4
  (:require [cryptopals.break-single-char-xor :refer [best-decoding string-score]]))

(defn detect-best-from [inputs]
  (->>
   inputs
   (map best-decoding)
   (apply max-key string-score)))
