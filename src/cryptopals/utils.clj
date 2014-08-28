(ns cryptopals.utils)

(defn avg [coll]
  (/ (reduce + coll)
     (count coll)))

(defn read-hex [hexstr]
  (->>
   hexstr
   (re-seq #"..")
   (map #(Integer/parseInt % 16))))

(defn sorted-map-by-value [coll]
  (into (sorted-map-by (fn [k1 k2]
                         (compare [(get coll k2) k2]
                                  [(get coll k1) k1])))
        coll))

(defn map-keys [f coll]
  (into {} (for [[k v] coll] [(f k) v])))

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
