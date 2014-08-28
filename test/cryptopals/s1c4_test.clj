(ns cryptopals.s1c4-test
  (:require [clojure.test :refer :all]
            [cryptopals.utils :as utils]
            [cryptopals.s1c4 :refer :all]))

(defn slurp-lines [pathname]
  (map utils/read-hex (re-seq #"[^\n]*" (slurp pathname))))

(deftest detect-best-test
  (let [lines (slurp-lines "./data/s1c4.txt")
        ; detecting all the lines is too slow to include in a test suite
        some-lines (take 25 (drop 325 lines))]
    (is (= (detect-best-from some-lines) "Now that the party is jumping\n"))))
