(ns cryptopals.utils)

(defn read-hex [hexstr]
  (map #(Integer/parseInt % 16) (re-seq #".." hexstr)))

(defn sorted-map-by-value [m]
  (into (sorted-map-by (fn [k1 k2]
                         (compare [(get m k2) k2]
                                  [(get m k1) k1])))
        m))

(defn map-keys [f m]
  (into {} (for [[k v] m] [(f k) v])))

(defn normalize [seq]
  (let [sum (apply + seq)]
    (map #(/ % sum) seq)))

(defn normalize-map [m]
  (into {} (map vector (keys m) (normalize (vals m)))))
