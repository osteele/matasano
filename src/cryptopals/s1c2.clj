(ns cryptopals.s1c2)

(defn xor-buffers [a b]
  (map bit-xor a b))
