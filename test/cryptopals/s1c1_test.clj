(ns cryptopals.s1c1-test
  (:require [clojure.test :refer :all]
            [cryptopals.s1c1 :refer :all]))

(deftest read-hex-test
  (let [test-input "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d"
        test-output "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t"]
    (is (= (base64 (read-hex test-input)) test-output))))
