(ns cryptopals.xor-cypher)

(defn xor-buffers [b1 b2]
  (->>
   (map bit-xor b1 b2)
   byte-array))

(defn encode [k input]
  (->>
   input
   (map int)
   (map bit-xor (cycle (map int k)))
   byte-array))

(defn decode [k input]
  (->>
   input
   (map int)
   (map bit-xor (cycle (map int k)))
   (map (partial bit-and 255))
   (map char)
   (apply str)))
