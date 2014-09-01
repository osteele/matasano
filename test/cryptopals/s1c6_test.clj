(ns cryptopals.s1c6-test
  (:use midje.sweet cryptopals.break-xor-cypher)
  (:require [cryptopals.utils :as utils]
            [cryptopals.base64 :as base64]
            [cryptopals.xor-cypher :as xor]))

(fact
 "string-hamming-distance should work on the challenge test"
 (let [s0 "this is a test"
       s1 "wokka wokka!!!"]
   (string-hamming-distance s0 s1) => 37))

(fact
 "break-xor-cypher should work on the challenge test"
 (let [input (base64/decode (clojure.string/replace (slurp "./data/s1c6.txt") #"\n" ""))]
   (break-xor-cypher input) => (slurp "./data/s1c6.out")))
