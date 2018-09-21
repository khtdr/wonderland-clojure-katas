(ns alphabet-cipher.coder)

(defn char-idx [ch] (- (int ch) (int \a)))

(defn encode-char [keyword-char message-char]
  (.charAt "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
           (+ (char-idx keyword-char) (char-idx message-char))))

(defn decode-char [keyword-char cipher-char]
  (char (+ (int \a) (.indexOf
                     (subs "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvwxyz"
                           (char-idx keyword-char) (+ 26 (char-idx keyword-char)))
                     (str cipher-char)))))

(defn transcode [keyword message transcode-char]
  (loop [i 0 output ""]
    (if (= i (count message)) output
      (recur (+ 1 i) (str output
                          (transcode-char
                           (.charAt keyword (rem i (count keyword)))
                           (.charAt message i)))))))

(defn repeats-in? [word text]
  (cond (empty? text) true
        (> (count word) (count text)) (= text (subs word 0 (count text)))
        (= word (subs text 0 (count word))) (repeats-in? word (subs text (count word)))
        :else false))

(defn find-repeater [text]
  (do (loop [n 1]
        (if (>= n (count text)) text
            (if (repeats-in? (subs text 0 n) text)
              (subs text 0 n)
              (recur (+ 1 n)))))))

(defn encode [keyword message] (transcode keyword message encode-char))
(defn decode [keyword message] (transcode keyword message decode-char))
(defn decipher [cipher message] (find-repeater (transcode message cipher decode-char)))
