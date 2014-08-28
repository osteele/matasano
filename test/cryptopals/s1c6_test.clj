(ns cryptopals.s1c6-test
  (:require [clojure.test :refer :all]
            [cryptopals.break-xor-cypher :refer :all]
            [cryptopals.utils :as utils]
            [cryptopals.base64 :as base64]
            [cryptopals.xor-cypher :as xor]))

(deftest string-hamming-distance-test
  (let [s0 "this is a test"
        s1 "wokka wokka!!!"]
    (assert (= (string-hamming-distance s0 s1)) 371)))

(deftest break-xor-cypher-test
  (let [input (base64/decode (clojure.string/replace (slurp "./data/s1c6.txt") #"\n" ""))
        expected (slurp "./data/s1c6.out")]
    (is (= (break-xor-cypher input) expected))))
