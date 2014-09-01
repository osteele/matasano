(ns cryptopals.base64-test
  (:use midje.sweet cryptopals.test-helpers)
  (:require [cryptopals.base64 :as base64]
            [cryptopals.utils :refer [read-hex]]))

(fact
 "base64/encode should encode a byte vector"
 (base64/encode [0 0 0])  => "AAAA"
 (base64/encode [4 0 0])  => "BAAA"
 (base64/encode [0 16 0]) => "ABAA"
 (base64/encode [0 0 64]) => "AABA"
 (base64/encode [1 0 0])  => "AQAA"
 (base64/encode [0 1 0])  => "AAEA"
 (base64/encode [0 0 1])  => "AAAB"
 (base64/encode [0 0 0 1 0 0]) => "AAAAAQAA"
 (base64/encode [0 0 0 0 1 0]) => "AAAAAAEA"
 (base64/encode [0 0 0 0 0 1]) => "AAAAAAAB"
 )

(fact
 "base64/encode should pad with =s"
 (base64/encode [0])    => "AA=="
 (base64/encode [1])    => "AQ=="
 (base64/encode [4])    => "BA=="
 (base64/encode [0 0])  => "AAA="
 (base64/encode [1 0])  => "AQA="
 (base64/encode [0 1])  => "AAE="
 (base64/encode [4 0])  => "BAA="
 (base64/encode [0 16]) => "ABA="
 )

(fact
 "base64/encode should decode substrings of the challenge string"
 (base64/encode (read-hex "49"))           => "SQ=="
 (base64/encode (read-hex "4927"))         => "SSc="
 (base64/encode (read-hex "49276d"))       => "SSdt"
 (base64/encode (read-hex "49276d20"))     => "SSdtIA=="
 (base64/encode (read-hex "49276d207b"))   => "SSdtIHs="
 (base64/encode (read-hex "49276d207b96")) => "SSdtIHuW"
 )

(fact
 "base64/decode should decode strings"
 (base64/decode "AAAA") => (just-bytes [0 0 0])
 (base64/decode "ABCD") => (just-bytes [0 16 131])
 (base64/decode "BAAA") => (just-bytes [4 0 0])
 (base64/decode "ABAA") => (just-bytes [0 16 0])
 (base64/decode "AABA") => (just-bytes [0 0 64])
 (base64/decode "AQAA") => (just-bytes [1 0 0])
 (base64/decode "AAEA") => (just-bytes [0 1 0])
 (base64/decode "AAAB") => (just-bytes [0 0 1])
 (base64/decode "AAAAAQAA") => (just-bytes [0 0 0 1 0 0])
 (base64/decode "AAAAAAEA") => (just-bytes [0 0 0 0 1 0])
 (base64/decode "AAAAAAAB") => (just-bytes [0 0 0 0 0 1]))

(fact
 "base64/decode should decode substrings of the challenge string"
 (base64/decode "SQ==")     => (just-bytes (read-hex "49"))
 (base64/decode "SSc=")     => (just-bytes (read-hex "4927"))
 (base64/decode "SSdt")     => (just-bytes (read-hex "49276d"))
 (base64/decode "SSdtIA==") => (just-bytes (read-hex "49276d20"))
 (base64/decode "SSdtIHs=") => (just-bytes (read-hex "49276d207b"))
 (base64/decode "SSdtIHuW") => (just-bytes (read-hex "49276d207b96"))
 )

(fact
 "base64/decode should decode padded string"
 (base64/decode "AA==") => (just-bytes [0])
 (base64/decode "AQ==") => (just-bytes [1])
 (base64/decode "BA==") => (just-bytes [4])
 (base64/decode "AAA=") => (just-bytes [0 0])
 (base64/decode "AQA=") => (just-bytes [1 0])
 (base64/decode "AAE=") => (just-bytes [0 1])
 (base64/decode "BAA=") => (just-bytes [4 0])
 (base64/decode "ABA=") => (just-bytes [0 16])
 )

(fact
 "base64/decode should ignore whitespace"
 (base64/decode "ABCD") => (just-bytes [0 16 131])
 (base64/decode "A BCD") => (just-bytes [0 16 131])
 (base64/decode "AB\nCD") => (just-bytes [0 16 131])
 )
