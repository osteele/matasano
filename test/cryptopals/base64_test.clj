(ns cryptopals.base64-test
  (:require [clojure.test :refer :all]
            [cryptopals.utils :refer [read-hex]]
            [cryptopals.base64 :as base64 :reload true]))

(deftest base64-encode-test
  (is (= (base64/encode [0 0 0]) "AAAA"))
  (is (= (base64/encode [4 0 0]) "BAAA"))
  (is (= (base64/encode [0 16 0]) "ABAA"))
  (is (= (base64/encode [0 0 64]) "AABA"))
  (is (= (base64/encode [1 0 0]) "AQAA"))
  (is (= (base64/encode [0 1 0]) "AAEA"))
  (is (= (base64/encode [0 0 1]) "AAAB"))
  (is (= (base64/encode [0 0 0 1 0 0]) "AAAAAQAA"))
  (is (= (base64/encode [0 0 0 0 1 0]) "AAAAAAEA"))
  (is (= (base64/encode [0 0 0 0 0 1]) "AAAAAAAB"))
  (is (= (base64/encode (read-hex "49276d")) "SSdt"))
  (is (= (base64/encode (read-hex "49276d207b96")) "SSdtIHuW"))
  (is (thrown? java.lang.AssertionError (base64/encode (read-hex "49"))))
  (is (thrown? java.lang.AssertionError (base64/encode (read-hex "49276d20"))))
  )

(deftest base64-decode-test
  (is (= (base64/decode "AAAA") [0 0 0]))
  (is (= (base64/decode "BAAA") [4 0 0]))
  (is (= (base64/decode "ABAA") [0 16 0]))
  (is (= (base64/decode "AABA") [0 0 64]))
  (is (= (base64/decode "AQAA") [1 0 0]))
  (is (= (base64/decode "AAEA") [0 1 0]))
  (is (= (base64/decode "AAAB") [0 0 1]))
  (is (= (base64/decode "AAAAAQAA") [0 0 0 1 0 0]))
  (is (= (base64/decode "AAAAAAEA") [0 0 0 0 1 0]))
  (is (= (base64/decode "AAAAAAAB") [0 0 0 0 0 1]))
  (is (= (base64/decode "SSdt") (read-hex "49276d")))
  (is (= (base64/decode "SSdtIHuW") (read-hex "49276d207b96")))
  )
