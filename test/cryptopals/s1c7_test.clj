(ns cryptopals.s1c7-test
  (:use midje.sweet)
  (:require [cryptopals.aes :as aes]
            [cryptopals.base64 :as base64]))

(fact
 (aes/decrypt (base64/decode (slurp "./data/s1c7.txt")) "YELLOW SUBMARINE")
 ; same plaintext as s1c6
 => (slurp "./data/s1c6-plain.txt"))
