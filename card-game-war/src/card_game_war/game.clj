(ns card-game-war.game)

(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])

(defn make-deck []
  (for [suit suits, rank ranks]
    {:suit suit :rank rank}))

(defn split-rand [coll]
  (split-at (rand-int (count coll)) coll))

(defn shuffle [deck]
  (do (loop [shuffled [] unshuffled deck]
        (if (empty? unshuffled) shuffled
            (let [[part-1 part-2] (split-rand unshuffled)]
              (recur (cons (first part-2) shuffled)
                     (concat part-1 (drop 1 part-2))))))))

(defn deal [deck] (split-at (quot (count deck) 2) deck))

(defn score-card [card]
  (+ (.indexOf suits (:suit card))
     (* 4 (.indexOf ranks (:rank card)))))

(defn winner [[card-1 card-2]]
  (if (> (score-card card-1)
         (score-card card-2))
    card-1 card-2))

(defn play-round [[hand-1 hand-2]]
  (let [card-1 (first hand-1)
        card-2 (first hand-2)]
    (if (= card-1 (winner [card-1 card-2]))
      [(concat (drop 1 hand-1) [(first hand-1) (first hand-2)]) (drop 1 hand-2)]
      [(drop 1 hand-1) (concat (drop 1 hand-2) [(first hand-2) (first hand-1)])])))

(defn someone-lost? [[hand-1 hand-2]]
  (or (= 0 (count hand-1))
      (= 0 (count hand-2))))

(defn end-game [[hand-1 hand-2] round-count]
  (print "Player" (if (> (count hand-1) (count hand-2)) "1" "2")
         "wins in" round-count "rounds!"))

(defn play-game [[player-1 player-2]]
  (do (loop [hand-1 player-1
             hand-2 player-2
             round-count 0]
        (if (someone-lost? [hand-1 hand-2])
          (end-game [hand-1 hand-2] round-count)
          (let [[next-hand-1 next-hand-2] (play-round [hand-1 hand-2])]
            (recur next-hand-1 next-hand-2 (+ 1 round-count)))))))
