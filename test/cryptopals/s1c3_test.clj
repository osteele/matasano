(ns cryptopals.s1c3-test
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer :all]
            [cryptopals.s1c3 :refer :all]))

(deftest frequencies-test
  (is (= (count english-letter-freqs) 26))
  (is (= (count english-char-freqs) 27))
  )

(deftest best-decoding-test
  (let [test-input (read-hex "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")]
    (is (= (find-best-key test-input) 88))
    (is (= (best-decoding test-input) "Cooking MC's like a pound of bacon"))
    ))
