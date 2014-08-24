(ns cryptopals.s1c1)

(defn read-hex [hexstr]
  (map #(Integer/parseInt % 16) (re-seq #".." hexstr)))

(def base64-index
  (letfn [(char-range [c0 c1]
                      (apply str (map #(char %) (range (int c0) (inc (int c1))))))]
    (str (char-range \A \Z) (char-range \a \z) (char-range \0 \9) "+/")))

(defn repack [xs]
  (->>
   (map (fn [a b i]
          (bit-or (bit-shift-left a (- 8 i)) (bit-shift-right b i)))
        (concat '(0) xs)
        (concat xs '(0))
        '(2 4 6 8))
   (map #(bit-and 63 %))
   ))

(defn base64 [seq]
  (->>
   seq
   (partition 3)
   (mapcat repack)
   (map #(get base64-index %))
   (apply str)))

(def test-input "49276d206b696c6c696e6720796f757220627261696e206c696b65206120706f69736f6e6f7573206d757368726f6f6d")
(def test-output "SSdtIGtpbGxpbmcgeW91ciBicmFpbiBsaWtlIGEgcG9pc29ub3VzIG11c2hyb29t")
(assert (= (base64 (read-hex test-input)) test-output))
