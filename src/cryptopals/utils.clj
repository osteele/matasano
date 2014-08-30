(ns cryptopals.utils)

; sequences

(defn avg [coll]
  (/ (reduce + coll)
     (count coll)))

(defn psort-by
  ([keyfn coll]
   (psort-by keyfn compare coll))
  ([keyfn comparefn coll]
    (->>
     coll
     (pmap #(list (keyfn %) %))
     (sort-by first comparefn)
     (map second))))

(defn pmax-key [keyfn & more]
  (first (psort-by keyfn > more)))

(defn pmin-key [keyfn & more]
  (first (psort-by keyfn < more)))

(defn transpose [coll]
  (apply mapv vector coll))

; strings

(defn read-hex [hexstr]
  (->>
   hexstr
   (re-seq #"..")
   (map #(Integer/parseInt % 16))))

; maps

(defn sorted-map-by-value [coll]
  (into (sorted-map-by (fn [k1 k2]
                         (compare [(get coll k2) k2]
                                  [(get coll k1) k1])))
        coll))

(defn map-keys [f coll]
  (zipmap (map f (keys coll)) (vals coll)))

(defn normalize [coll]
  (let [sum (apply + coll)]
    (map #(/ % sum) coll)))

(defn normalize-map-values [coll]
  (into {} (map vector
                (keys coll)
                (normalize (vals coll)))))

(defn map-values-dot-product [m1 m2]
  (->>
   m1
   (map (fn [[k v]] (* (get m2 k 0) v)))
   (reduce +)))
