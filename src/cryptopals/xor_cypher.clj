(ns cryptopals.xor-cypher)

(defn xor-buffers [a b]
  (map bit-xor a b))

(defn encode [k input]
  (->>
   input
   (map int)
   (map bit-xor (cycle (map int k)))))

(defn decode [k input]
  (->>
   input
   (encode k)
   (map char)
   (apply str)))
