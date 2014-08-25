(ns cryptopals.xor-cypher)

(defn xor-buffers [a b]
  (map bit-xor a b))

(defn encode [k text]
  (->>
   text
   (map int)
   (map bit-xor (cycle (map int k)))))

(defn decode [k text]
  (->>
   text
   (encode k)
   (map char)
   (apply str)))
