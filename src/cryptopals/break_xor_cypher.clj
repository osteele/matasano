(ns cryptopals.break-xor-cypher
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer [avg pmin-key transpose]]
            [cryptopals.xor-cypher :as xor]
            [cryptopals.break-single-char-xor :as break-single-xor]))

(defn bit-count [n]
  (let [n (int n)
        n (- n (bit-and (bit-shift-right n 1) 0x55555555))
        n (+ (bit-and n 0x33333333) (bit-and (bit-shift-right n 2) 0x33333333))
        n (bit-shift-right (* (bit-and (+ n (bit-shift-right n 4)) 0x0F0F0F0F) 0x01010101) 24)
        ]
    n))

(defn char-hamming-distance [c0 c1]
  (bit-count (bit-xor (int c0) (int c1))))

(defn string-hamming-distance [s0 s1]
  (reduce + 0 (map char-hamming-distance s0 s1)))

(defn hamming-distance-for-keysize [input keysize]
  (let [blocks (filter #(= (count %) keysize) (partition keysize input))
        ;block-count (max 5 (int (/ 20 keysize)))
        block-count 10
        ]
    (/ (avg (map #(string-hamming-distance (first blocks) %)
                 (take block-count (rest blocks))))
       (float keysize))))

(defn detect-xor-keysize [input]
  (apply min-key #(hamming-distance-for-keysize input %) (range 1 (inc 40))))

(defn find-xor-key [input]
  (let [keysize (detect-xor-keysize input)]
    (->>
     input
     (partition keysize)
     transpose
     (pmap break-single-xor/find-best-key)
     (map char)
     (apply str))))

(defn break-xor-cypher [input]
  (let [xor-key (find-xor-key input)]
    (xor/decode xor-key input)))
