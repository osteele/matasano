(defn read-hex [hexstr]
  (map #(Integer/parseInt % 16) (re-seq #".." hexstr)))

(defn xor-buffers [a b]
  (map bit-xor a b))

(def test-input-1 (read-hex "1c0111001f010100061a024b53535009181c"))
(def test-input-2 (read-hex "686974207468652062756c6c277320657965"))
(def test-output (read-hex "746865206b696420646f6e277420706c6179"))

(assert (= (xor-buffers test-input-1 test-input-2) test-output))
