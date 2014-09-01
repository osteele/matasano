(ns cryptopals.s1c7-test
  (:use midje.sweet)
  (:require [cryptopals.base64 :as base64]))

(import (java.security Key)
        (javax.crypto Cipher)
        (javax.crypto.spec SecretKeySpec))

(defn aes-decrypt [input k]
 (let [secret (SecretKeySpec. (.getBytes k) "AES")
       cipher (Cipher/getInstance "AES/ECB/PKCS5Padding")]
   (.init cipher Cipher/DECRYPT_MODE secret)
   (String. (.doFinal cipher input) "UTF-8")))

(fact
 (aes-decrypt (base64/decode (slurp "./data/s1c7.txt")) "YELLOW SUBMARINE")
 ; same plaintext as s1c6
 => (slurp "./data/s1c6-plain.txt"))
