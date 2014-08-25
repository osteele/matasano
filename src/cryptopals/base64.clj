(ns cryptopals.base64
  (:require [clojure.string :as string]))

(def ^:private base64-index
  (letfn [(char-range [c0 c1]
                      (string/join (map char (range (int c0) (inc (int c1))))))]
    (str (char-range \A \Z) (char-range \a \z) (char-range \0 \9) "+/")))

(defn- repack [xs]
  (->>
   (map (fn [a b i]
          (bit-or (bit-shift-left a (- 8 i)) (bit-shift-right b i)))
        (concat '(0) xs)
        (concat xs '(0))
        '(2 4 6 8))
   (map #(bit-and 63 %))
   ))

(defn base64-decode [^String encoded]
  (->>
   encoded
   (partition 3)
   (mapcat repack)
   (map #(get base64-index %))
   (apply str)))
