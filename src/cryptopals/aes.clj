(ns cryptopals.aes)

(import (java.security Key)
        (javax.crypto Cipher)
        (javax.crypto.spec SecretKeySpec))

(defn decrypt [input k]
 (let [secret (SecretKeySpec. (.getBytes k) "AES")
       cipher (Cipher/getInstance "AES/ECB/PKCS5Padding")]
   (.init cipher Cipher/DECRYPT_MODE secret)
   (String. (.doFinal cipher input) "UTF-8")))
