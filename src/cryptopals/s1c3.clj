(ns cryptopals.s1c3
  (:require [cryptopals.utils :as utils]))

(defn repeating-key-xor [key buffer]
  (map bit-xor buffer (cycle key)))

(defn decode [key input]
  (->>
   input
   (repeating-key-xor key)
   (map char)
   (apply str)))

(defn ordered-key-string [seq]
  (->>
   seq
   (map char)
   (map #(Character/toUpperCase %))
   frequencies
   utils/sorted-map-by-value
   keys
   (apply str)
   ))

(def english-letter-freqs
  [11.602 4.702 3.511 2.670 2.007 3.779 1.950 7.232 6.286 0.597 0.590 2.705 4.374 2.365 6.264 2.545 0.173 1.653 7.775 16.671 1.487 0.649 6.753 0.017 1.620 0.034])

(def english-char-freqs
  (->>
   (map (fn [k v] [(char (+ 65 k)) v])
        (range 26)
        english-letter-freqs)
   (into {\space 11.7})
   utils/normalize-map))

(defn score-frequencies [freqs]
  (->>
   freqs
   utils/normalize-map
   (map (fn [[k v]] (* (get english-char-freqs k 0) v)))
   (apply +)))

(defn score-for-key [key input]
  (->>
   input
   (repeating-key-xor key)
   (map char)
   (map #(Character/toUpperCase %))
   frequencies
   score-frequencies
   ))

(defn sorted-keys [input]
  (let [keys (map list (range 255))
        scored-keys (map #(with-meta % {:score (score-for-key % input)}) keys)
        sorted-keys (sort-by (comp :score meta) > scored-keys)
        ]
    sorted-keys))

(defn find-best-key [input]
  (first (sorted-keys input)))

(defn best-decoding [input]
  (let [key (find-best-key input)]
    (decode key input)))
