(ns cryptopals.s1c4
  (:require [cryptopals.s1c3 :refer [best-decoding string-score]]
            [cryptopals.utils :as utils]))

(defn detect-best-from [inputs]
  (->>
   inputs
   (map best-decoding)
   (apply max-key #(string-score %))))

(defn slurp-lines [pathname]
  (map utils/read-hex (re-seq #"[^\n]*" (slurp pathname))))


;(defn detect-best-in-file [pathname]
;  (let [lines (map utils/read-hex (re-seq #"[^\n]*" (slurp pathname)))]
;    (detect-best-from lines)
;    ))
