(ns cryptopals.break-xor-cypher
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer [avg]]
            [cryptopals.base64 :as base64]
            [cryptopals.xor-cypher :as xor]
            [cryptopals.s1c3 :as break-single-xor]))

(defn bit-count [n]
  (let [i0 (int n)
        i1 (- n (bit-and (bit-shift-right n 1) 0x55555555))
        i2 (+ (bit-and i1 0x33333333) (bit-and (bit-shift-right i1 2) 0x33333333))
        i3 (bit-shift-right (* (bit-and (+ i2 (bit-shift-right i2 4)) 0x0F0F0F0F) 0x01010101) 24)
        ]
    i3))

(defn char-hamming-distance [c0 c1]
  (bit-count (bit-xor (int c0) (int c1))))

(defn string-hamming-distance [s0 s1]
  (reduce + (map char-hamming-distance s0 s1)))

(defn hamming-distance-for-keysize [input keysize]
  (let [h1 (subvec input 0 keysize)
        h2 (subvec input keysize (* keysize 2))]
    (/ (string-hamming-distance h1 h2) (float keysize))))

(defn hamming-distance-for-keysize [input keysize]
  (let [chunks (filter #(= (count %) keysize) (partition keysize input))
        ;chunk-count (max 5 (int (/ 20 keysize)))
        chunk-count 10
        ]
    (/ (avg (map #(string-hamming-distance (first chunks) %)
                 (take chunk-count (rest chunks))))
       (float keysize))))

(defn detect-xor-keysize [input]
  (apply min-key #(hamming-distance-for-keysize input %) (range 1 40)))

(defn transpose [columns input]
  (assert (zero? (mod (count input) columns)))
  (let [chunks (map vec (partition columns input))]
    (for [i (range columns)]
      (map #(% i) chunks))))

(defn find-xor-key [input]
  (let [max-keysize 40
        keysize (detect-xor-keysize input)
        ]
    (->>
     (subvec input 0 (* keysize (int (/ (count input) keysize))))
     (transpose keysize)
     (map break-single-xor/find-best-key)
     (map char)
     (apply str))))

(defn break-xor-cypher [input]
  (let [xor-key (find-xor-key input)]
    (xor/decode xor-key input)))
