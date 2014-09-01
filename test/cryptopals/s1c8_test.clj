(ns cryptopals.s1c8-test
  (:use midje.sweet)
  (:require [cryptopals.aes :as aes]
            [cryptopals.base64 :as base64]))

(defn count-duplicate-blocks [input]
  (->>
   input
   (partition 16)
   (#(- (count %) (count (set %))))))

(fact
 (let [lines (with-open [rdr (clojure.java.io/reader "./data/s1c8.txt")]
               (apply list (line-seq rdr)))
       inputs (map base64/decode lines)
       best (apply max-key count-duplicate-blocks inputs)]
   inputs => (contains best)
   (count-duplicate-blocks best) = 3
   ))
