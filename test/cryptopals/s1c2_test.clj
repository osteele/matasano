(ns cryptopals.s1c2-test
  (:use midje.sweet cryptopals.xor-cypher)
  (:require [cryptopals.utils :refer [read-hex]]))

(fact
 "xor-buffers should xor two buffers"
 (let [test-input-1 (read-hex "1c0111001f010100061a024b53535009181c")
       test-input-2 (read-hex "686974207468652062756c6c277320657965")
       test-output (read-hex "746865206b696420646f6e277420706c6179")]
   (xor-buffers test-input-1 test-input-2) => test-output))
