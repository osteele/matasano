(ns cryptopals.utils-test
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer :all]))

(deftest avg-test
  (is (= (avg '(1 2 3)) 2)))

(deftest psort-by-test
  (is (= (psort-by identity [1 3 2 4]) [1 2 3 4]))
  (is (= (psort-by identity < [1 3 2 4]) [1 2 3 4]))
  (is (= (psort-by identity > [1 3 2 4]) [4 3 2 1]))
  (is (= (psort-by identity ["these" "are" "some" "strings"])
         ["are" "some" "strings" "these"]))
  (is (= (psort-by count ["these" "are" "some" "strings"])
         ["are" "some" "these" "strings"]))
  (is (= (psort-by count > ["these" "are" "some" "strings"])
         ["strings" "these" "some" "are"]))
  )

(deftest pmax-key-test
  (is (= (pmax-key identity 1 3 2 4) 4))
  (is (= (pmax-key #(- %) 1 3 2 4) 1))
  (is (= (pmax-key count "these" "are" "some" "strings") "strings"))
  )

(deftest sorted-map-by-value-test
  (is (= (sorted-map-by-value {\a 1 \b 10 \c 2 \d 1})
         {\b 10 \c 2 \a 1 \d 1})))

(deftest map-keys-test
  (is (= (map-keys inc {1 \a 2 \b}) {2 \a 3 \b})))
