(ns cryptopals.s1c3)

(defn read-hex [hexstr]
  (map #(Integer/parseInt % 16) (re-seq #".." hexstr)))

(defn repeating-key-xor [key buffer]
  (map bit-xor buffer (cycle key)))

(defn sorted-map-by-value [m]
  (into (sorted-map-by (fn [k1 k2]
                         (compare [(get m k2) k2]
                                  [(get m k1) k1])))
        m))

(defn map-keys [f m]
  (into {} (for [[k v] m] [(f k) v])))

(defn ordered-key-string [seq]
  (->>
   seq
   (map char)
   (map #(Character/toUpperCase %))
   frequencies
   sorted-map-by-value
   keys
   (apply str)
   ))

(defn normalize [seq]
  (let [sum (apply + seq)]
    (map #(/ % sum) seq)))

(defn normalize-map [m]
  (into {} (map vector (keys m) (normalize (vals m)))))

(def english-letter-freqs
  [11.602 4.702 3.511 2.670 2.007 3.779 1.950 7.232 6.286 0.597 0.590 2.705 4.374 2.365 6.264 2.545 0.173 1.653 7.775 16.671 1.487 0.649 6.753 0.017 1.620 0.034])

(def english-char-freqs
  (->>
   (map (fn [k v] [(char (+ 65 k)) v])
        (range 26)
        english-letter-freqs)
   (into {\space 11.7})
   normalize-map))

(defn score-frequencies [freqs]
  (->>
   freqs
   normalize-map
   (map (fn [[k v]] (* (get english-char-freqs k 0) v)))
   (apply +)))

(defn score-for-key [key input]
  (->>
   input
   (repeating-key-xor key)
   (map char)
   (map #(Character/toUpperCase %))
   frequencies
   score-frequencies
   ))

(defn find-best-key [input]
  (let [scored-keys
        (for [k (map list (range 255))] [k (score-for-key k input)])]
    (ffirst (sort-by second > scored-keys))))

(defn decode [input]
  (let [key (find-best-key input)]
    (->>
     input
     (repeating-key-xor key)
     (map char)
     (apply str))))
