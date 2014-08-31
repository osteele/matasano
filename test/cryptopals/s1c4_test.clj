(ns cryptopals.s1c4-test
  (:use midje.sweet cryptopals.s1c4)
  (:require [clojure.string :as string]
            [cryptopals.utils :as utils]))

(defn slurp-hex-lines [pathname]
  (->>
   (slurp pathname)
   (re-seq #"[^\n]*") ; TODO string/split-lines
   (map utils/read-hex)))

(fact
 "should detect the encrypted string from a list of ciphers"
 (let [lines (slurp-hex-lines "./data/s1c4.txt")
       ; detecting all the lines is too slow to include in a test suite
       some-lines (take 25 (drop 325 lines))]
   (detect-best-from some-lines) => "Now that the party is jumping\n"))
