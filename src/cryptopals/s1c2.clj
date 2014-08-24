(ns cryptopals.s1c2)

(defn read-hex [hexstr]
  (map #(Integer/parseInt % 16) (re-seq #".." hexstr)))

(defn xor-buffers [a b]
  (map bit-xor a b))
