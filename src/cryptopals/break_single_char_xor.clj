(ns cryptopals.break-single-char-xor
  (:require [cryptopals.utils :as utils]
            [cryptopals.xor-cypher :as xor]))

(def english-letter-freq-vector
  ; source: http://en.wikipedia.org/wiki/Letter_frequency#Relative_frequencies_of_letters_in_the_English_language
  [11.602 4.702 3.511 2.670 2.007 3.779 1.950 7.232 6.286 0.597 0.590 2.705 4.374 2.365 6.264 2.545 0.173 1.653 7.775 16.671 1.487 0.649 6.753 0.017 1.620 0.034])

(def english-char-freq-map
  (->>
   (map (fn [k v] [(char (+ 65 k)) v])
        (range 26)
        english-letter-freq-vector)
   (into {\space 11.7})
   utils/normalize-map-values))

(defn string-score [input]
  (->>
   input
   clojure.string/upper-case
   frequencies
   utils/normalize-map-values
   (utils/map-values-dot-product english-char-freq-map)))

(defn- score-for-key [k input]
  (->>
   (xor/decode [k] input)
   string-score))

(defn- sorted-keys [input]
  (->>
   (range 256)
   (sort-by #(score-for-key % input) >)))

(defn find-best-key [input]
  (->>
   (range 256)
   (apply max-key #(score-for-key % input) )))

(defn decodings [input]
  (->>
   (sorted-keys input)
   (map #(xor/decode % input))))

(defn best-decoding [input]
  (xor/decode [(find-best-key input)] input))
