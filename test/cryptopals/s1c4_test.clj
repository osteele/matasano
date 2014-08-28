(ns cryptopals.s1c4-test
  (:require [clojure.test :refer :all]
            [clojure.string :as string]
            [cryptopals.utils :as utils]
            [cryptopals.s1c4 :refer :all]))

(defn slurp-hex-lines [pathname]
  (->>
   (slurp pathname)
   (re-seq #"[^\n]*") ; TODO string/split-lines
   (map utils/read-hex)))

(deftest detect-best-test
  (let [lines (slurp-hex-lines "./data/s1c4.txt")
        ; detecting all the lines is too slow to include in a test suite
        some-lines (take 25 (drop 325 lines))]
    (is (= (detect-best-from some-lines) "Now that the party is jumping\n"))))
