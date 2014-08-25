(ns cryptopals.s1c2-test
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer [read-hex]]
            [cryptopals.xor-cypher :refer :all]))

(deftest xor-buffers-test
  (let [test-input-1 (read-hex "1c0111001f010100061a024b53535009181c")
        test-input-2 (read-hex "686974207468652062756c6c277320657965")
        test-output (read-hex "746865206b696420646f6e277420706c6179")]
    (is (= (xor-buffers test-input-1 test-input-2) test-output))))
