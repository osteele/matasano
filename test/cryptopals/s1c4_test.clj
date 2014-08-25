(ns cryptopals.s1c4-test
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer :all]
            [cryptopals.s1c4 :refer :all]))

(deftest detect-best-test
  (let [lines (slurp-lines "./data/s1c4.txt")
        some-lines (take 25 (drop 325 lines))]
    (is (= (detect-best-from some-lines) "Now that the party is jumping\n"))))
