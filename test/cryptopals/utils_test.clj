(ns cryptopals.utils-test
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer :all]))

(deftest sorted-map-by-value-test
  (is (= (sorted-map-by-value {\a 1 \b 10 \c 2 \d 1})
         {\b 10 \c 2 \a 1 \d 1})))

(deftest map-keys-test
  (is (= (map-keys inc {1 \a 2 \b}) {2 \a 3 \b})))
