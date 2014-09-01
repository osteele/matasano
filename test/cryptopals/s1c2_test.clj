(ns cryptopals.s1c2-test
  (:use midje.sweet cryptopals.xor-cypher cryptopals.test-helpers)
  (:require [cryptopals.utils :refer [read-hex]]))

(fact
 "xor-buffers should xor two buffers"
 (xor-buffers (read-hex "1c0111001f010100061a024b53535009181c")
              (read-hex "686974207468652062756c6c277320657965"))
 => (just-bytes (read-hex "746865206b696420646f6e277420706c6179")))
