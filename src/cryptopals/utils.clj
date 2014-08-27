(ns cryptopals.utils)

(defn read-hex [hexstr]
  (map #(Integer/parseInt % 16) (re-seq #".." hexstr)))

(defn sorted-map-by-value [coll]
  (into (sorted-map-by (fn [k1 k2]
                         (compare [(get coll k2) k2]
                                  [(get coll k1) k1])))
        coll))

(defn avg [xs]
  (/ (reduce + 0 xs) (count xs)))

(defn map-keys [f coll]
  (into {} (for [[k v] coll] [(f k) v])))

(defn normalize [coll]
  (let [sum (apply + coll)]
    (map #(/ % sum) coll)))

(defn normalize-map [coll]
  (into {} (map vector (keys coll) (normalize (vals coll)))))
