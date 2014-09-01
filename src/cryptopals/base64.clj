(ns cryptopals.base64
  ;(:use midje.sweet)
  (:require [clojure.string :as string]))

(def ^:private char-encodings
  (letfn [(char-range [c0 c1]
                      (string/join (map char (range (int c0) (inc (int c1))))))]
    (str
     (char-range \A \Z)
     (char-range \a \z)
     (char-range \0 \9)
     "+/=")))

(def ^:private char->int-map
  (assoc (zipmap char-encodings (range (count char-encodings)))
    \= 0))

(defn- octets->sextets
  ([a]
   (concat (take 2 (octets->sextets a 0 0)) [64 64]))
  ([a b]
   (concat (take 3 (octets->sextets a b 0)) [64]))
  ([a b c]
   (->>
    (map (fn ([a b i]
              (bit-or (bit-shift-left a (- 8 i)) (bit-shift-right b i))))
         (concat [0 a b c])
         (concat [a b c 0])
         [2 4 6 8])
    (map #(bit-and 63 %))
    )))

(defn- sextets->octets
  ([a b]
   (take 1 (sextets->octets a b 0 0)))
  ([a b c]
   (take 2 (sextets->octets a b c 0)) )
  ([a b c d]
   (->>
    (map (fn [a b i]
           (bit-or (bit-shift-left a i) (bit-shift-right b (- 6 i))))
         [a b c]
         [b c d]
         [2 4 6])
    (map #(bit-and 255 %))
    )))

(defn encode [input]
  (->>
   input
   (partition-all 3)
   (mapcat #(apply octets->sextets %))
   (map #(nth char-encodings %))
   (apply str)))

(defn decode [input]
  (let [input (string/replace input #"\s" "")]
    (assert (zero? (mod (count input) 4)))
    (->>
     (clojure.string/replace input #"=+$" "")
     (map char->int-map)
     (partition-all 4)
     (mapcat #(apply sextets->octets %))
     (apply vector)
     )))
