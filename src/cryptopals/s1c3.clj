(ns cryptopals.s1c3
  (:require [cryptopals.utils :as utils]
            [cryptopals.xor-cypher :as xor]))

(def english-letter-freqs
  [11.602 4.702 3.511 2.670 2.007 3.779 1.950 7.232 6.286 0.597 0.590 2.705 4.374 2.365 6.264 2.545 0.173 1.653 7.775 16.671 1.487 0.649 6.753 0.017 1.620 0.034])

(def english-char-freqs
  (->>
   (map (fn [k v] [(char (+ 65 k)) v])
        (range 26)
        english-letter-freqs)
   (into {\space 11.7})
   utils/normalize-map))

(defn- score-frequencies [coll]
  (->>
   coll
   utils/normalize-map
   (map (fn [[k v]] (* (get english-char-freqs k 0) v)))
   (apply +)))

(defn string-score [s]
  (->>
   s
   clojure.string/upper-case
   frequencies
   score-frequencies))

(defn- score-for-key [k s]
  (->>
   (xor/decode k s)
   string-score
   ))

(defn- sorted-keys [s]
  (->>
   (map list (range 255))
   (map #(with-meta % {:score (score-for-key % s)}))
   (sort-by (comp :score meta) >)))

(defn find-best-key [s]
  (first (sorted-keys s)))

(defn best-decoding [s]
  (let [k (find-best-key s)]
    (xor/decode k s)))
