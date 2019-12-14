(ns doublets.solver
  (:require [clojure.java.io :as io]
            [clojure.edn :as edn]))

(def words (-> "words.edn"
               (io/resource)
               (slurp)
               (read-string)))

(defn abs [n] (max n (- n)))


(defn diff [a b]
  (+ (abs (- (count a) (count b)))
     (loop [w1 a w2 b dd 0]
       (if (or (empty? w1) (empty? w2)) dd
           (recur (rest w1) (rest w2) (+ dd (if (= (first w1) (first w2)) 0 1)))))))

(def edges
  (->> (for [a words b words] [a b])
       (filter #(= 1 (diff (first %) (last %))))))

(def next-words
  (->> words
       (map (fn [word]
              [word (->> edges
                         (filter #(= word (first %)))
                         (map #(last %)))]))
       (into (sorted-map))))

(defn in? [coll val] (some #(= val %) coll))

(defn doublets [word1 word2]
  (loop [q [word1] path [] seen []]
    (cond (empty? q) []
          (= word2 (first q)) (reverse (cons (first q) path))
          (in? seen (first q)) (recur (drop 1 q) path seen)
          :else (recur (drop 1 (concat q (next-words (first q))))
                       (cons (first q) path)
                       (into seen [(first q)])))))

;(doublets "door" "lock")

;          (doublets "wheat" "bread")
;           (doublets "bank" "note")
;(next-words "head")
