(ns cryptopals.base64
  (:require [clojure.string :as string]))

(def ^:private char-encodings
  (letfn [(char-range [c0 c1]
                      (string/join (map char (range (int c0) (inc (int c1))))))]
    (str
     (char-range \A \Z)
     (char-range \a \z)
     (char-range \0 \9)
     "+/")))

(def ^:private char->int-map
  (assoc (zipmap char-encodings (range (count char-encodings)))
    \= 0))

(defn- octets->sextets [xs]
  (->>
   (map (fn [a b i]
          (bit-or (bit-shift-left a (- 8 i)) (bit-shift-right b i)))
        (concat [0] xs)
        (concat xs [0])
        [2 4 6 8])
   (map #(bit-and 63 %))))

(defn- sextets->octets [xs]
  (->>
   (map (fn [a b i]
          (bit-or (bit-shift-left a i) (bit-shift-right b (- 6 i))))
        xs
        (rest xs)
        [2 4 6])
   (map #(bit-and 255 %))))

(defn encode [s]
  (assert (zero? (mod (count s) 3)))
  (->>
   s
   (partition 3)
   (mapcat octets->sextets)
   (map #(nth char-encodings %))
   (apply str)))

(defn decode [s]
  (assert (zero? (mod (count s) 4)))
  (->>
   s
   (map char->int-map)
   ;(map assert)
   (partition 4)
   (mapcat sextets->octets)
   (apply vector)
   ))
