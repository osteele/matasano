(ns cryptopals.test-helpers
  (:use midje.sweet))

(defn just-bytes [expected]
  (chatty-checker [actual]
    (= (map (partial bit-and 255) expected)
       (map (partial bit-and 255) actual))))
