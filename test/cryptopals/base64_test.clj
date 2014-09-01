(ns cryptopals.base64-test
  (:use midje.sweet)
  (:require [cryptopals.base64 :as base64]
            [cryptopals.utils :refer [read-hex]]))

(fact
 "base64/encode should encode a byte vector"
 (base64/encode [0 0 0]) => "AAAA"
 (base64/encode [4 0 0]) => "BAAA"
 (base64/encode [0 16 0]) => "ABAA"
 (base64/encode [0 0 64]) => "AABA"
 (base64/encode [1 0 0]) => "AQAA"
 (base64/encode [0 1 0]) => "AAEA"
 (base64/encode [0 0 1]) => "AAAB"
 (base64/encode [0 0 0 1 0 0]) => "AAAAAQAA"
 (base64/encode [0 0 0 0 1 0]) => "AAAAAAEA"
 (base64/encode [0 0 0 0 0 1]) => "AAAAAAAB"
 )

(fact
 "base64/encode should pad with =s"
 (base64/encode [0]) => "AA=="
 (base64/encode [1]) => "AQ=="
 (base64/encode [4]) => "BA=="
 (base64/encode [0 0]) => "AAA="
 (base64/encode [1 0]) => "AQA="
 (base64/encode [0 1]) => "AAE="
 (base64/encode [4 0]) => "BAA="
 (base64/encode [0 16]) => "ABA="
 )

(fact
 "base64/encode should decode substrings from challenge string"
 (base64/encode (read-hex "49")) => "SQ=="
 (base64/encode (read-hex "4927")) => "SSc="
 (base64/encode (read-hex "49276d")) => "SSdt"
 (base64/encode (read-hex "49276d20")) => "SSdtIA=="
 (base64/encode (read-hex "49276d207b")) => "SSdtIHs="
 (base64/encode (read-hex "49276d207b96")) => "SSdtIHuW"
 )

(fact
 "base64/decode should decode a string"
 (base64/decode "AAAA") => [0 0 0]
 (base64/decode "ABCD") => [0 16 131]
 (base64/decode "BAAA") => [4 0 0]
 (base64/decode "ABAA") => [0 16 0]
 (base64/decode "AABA") => [0 0 64]
 (base64/decode "AQAA") => [1 0 0]
 (base64/decode "AAEA") => [0 1 0]
 (base64/decode "AAAB") => [0 0 1]
 (base64/decode "AAAAAQAA") => [0 0 0 1 0 0]
 (base64/decode "AAAAAAEA") => [0 0 0 0 1 0]
 (base64/decode "AAAAAAAB") => [0 0 0 0 0 1]
 (base64/decode "SSdt") => (read-hex "49276d")
 (base64/decode "SSdtIHuW") => (read-hex "49276d207b96")
 )

(fact
 "base64/decode should decode padded string"
 (base64/decode "AA==") => [0]
 (base64/decode "AQ==") => [1]
 (base64/decode "BA==") => [4]
 (base64/decode "AAA=") => [0 0]
 (base64/decode "AQA=") => [1 0]
 (base64/decode "AAE=") => [0 1]
 (base64/decode "BAA=") => [4 0]
 (base64/decode "ABA=") => [0 16]
 )

(fact
 "base64/decode should ignore whitespace"
 (base64/decode "ABCD") => [0 16 131]
 (base64/decode "A BCD") => [0 16 131]
 (base64/decode "AB\nCD") => [0 16 131]
 )
