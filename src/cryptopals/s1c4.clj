(ns cryptopals.s1c4
  (:require [cryptopals.break-single-char-xor :refer [best-decoding string-score]]
            [cryptopals.utils :refer [pmax-key]]))

(defn detect-best-from [inputs]
  (->>
   inputs
   (pmap best-decoding)
   (apply pmax-key string-score)))
