(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= [3 :spade]
           (winner [[2 :spade]
                    [3 :spade]])
           )))
  (testing "order doesn't matter"
    (is (= [4 :spade]
           (winner [[4 :spade]
                    [3 :spade]])
           )))
  (testing "jacks are higher rank than numbers"
    (is (= [:jack :spade]
           (winner [[10 :spade]
                    [:jack :spade]])
           )))
  (testing "queens are higher rank than jacks"
    (is (= [:queen :spade]
           (winner [[:queen :spade]
                    [:jack :spade]])
           )))
  (testing "kings are higher rank than queens"
    (is (= [:king :spade]
           (winner [[:queen :spade]
                    [:king :spade]])
           )))
  (testing "aces are higher rank than kings"
    (is (= [:ace :spade]
           (winner [[:king :spade]
                    [:ace :spade]])
           )))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= [:ace :club]
           (winner [[:ace :club]
                    [:ace :spade]])
           )))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= [:ace :diamond]
           (winner [[:ace :club]
                    [:ace :diamond]])
           )))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= [:ace :heart]
           (winner [[:ace :heart]
                    [:ace :diamond]])
           ))))

(deftest test-play-game
  (testing "player 2 has all the high cards, should win every hand"
    (is (= (with-out-str (play-game (deal (make-deck))))
           "Player 2 wins in 26 rounds!"
           )))
  (testing "player 1 has the low cards and Ace of Hearts, and should win"
    (is (= (with-out-str (play-game (deal (cons (last (make-deck)) (drop 1 (make-deck))))))
           "Player 1 wins in 246 rounds!"
           )))
  (testing "player 1 wins with ace of hearts against shuffled opponent"
    (let [deck (make-deck)]
      (is (= (subs (with-out-str
                     (play-game (deal (cons (last deck) (drop 1 (shuffle deck)))))
                     ) 0 13)
             "Player 1 wins"
             )
          ))))
