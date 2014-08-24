(ns cryptopals.s1c3-test
  (:require [clojure.test :refer :all]
            [cryptopals.s1c3 :refer :all]))

(deftest sorted-map-by-value-test
  (is (= (sorted-map-by-value {\a 1 \b 10 \c 2 \d 1})
         {\b 10 \c 2 \a 1 \d 1})))

(deftest map-keys-test
  (is (= (map-keys inc {1 \a 2 \b}) {2 \a 3 \b})))

(deftest frequencies-test
  (is (= (count english-letter-freqs) 26))
  (is (= (count english-char-freqs) 27))
  )

(deftest decode-test
  (let [test-input (read-hex "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")]
    (is (= (find-best-key test-input) '(88)))
    (is (= (decode test-input) "Cooking MC's like a pound of bacon"))
    ))
