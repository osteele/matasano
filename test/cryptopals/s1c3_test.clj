(ns cryptopals.s1c3-test
  (:use midje.sweet cryptopals.utils cryptopals.break-single-char-xor))

(fact
 "the english frequency tables should be the expected size"
 (count english-letter-freq-vector) => 26
 (count english-char-freq-map) => 27)

(fact
 "the english frequency table should sum to 1.0"
 (reduce + (vals english-char-freq-map)) => (roughly 1.0))

(fact
 "should detect and break single-character xor"
 (let [test-input (read-hex "1b37373331363f78151b7f2b783431333d78397828372d363c78373e783a393b3736")]
   (find-best-key test-input) => 88
   (best-decoding test-input) => "Cooking MC's like a pound of bacon"))
