(ns cryptopals.utils-test
  (:use midje.sweet cryptopals.utils))

(fact
 "avg should compute the average"
 (avg '(1 2 3)) => 2)

(fact
 "psort-by should act like sort-by"
 (psort-by identity [1 3 2 4]) => [1 2 3 4]
 (psort-by identity < [1 3 2 4]) => [1 2 3 4]
 (psort-by identity > [1 3 2 4]) => [4 3 2 1]
 (psort-by identity ["these" "are" "some" "strings"]) =>
 ["are" "some" "strings" "these"]
 (psort-by count ["these" "are" "some" "strings"]) =>
 ["are" "some" "these" "strings"]
 (psort-by count > ["these" "are" "some" "strings"]) =>
 ["strings" "these" "some" "are"]
 )

(fact
 "pmax-key should act like max-key"
 (pmax-key identity 1 3 2 4) => 4
 (pmax-key #(- %) 1 3 2 4) => 1
 (pmax-key count "these" "are" "some" "strings") "strings"
 )

(fact
 "sorted-map-by-value should sort the map by entry value"
 (sorted-map-by-value {\a 1 \b 10 \c 2 \d 1}) =>
 {\b 10 \c 2 \a 1 \d 1})

(fact
 "map-keys should map the function over the values"
 (map-keys inc {1 \a 2 \b}) => {2 \a 3 \b})
