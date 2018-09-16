(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (is (= {:rank 3 :suit :spade}
           (winner [{:rank 2 :suit :spade}
                    {:rank 3 :suit :spade}])
           )))
  (testing "order doesn't matter"
    (is (= {:rank 4 :suit :spade}
           (winner [{:rank 4 :suit :spade}
                    {:rank 3 :suit :spade}])
           )))
  (testing "jacks are higher rank than numbers"
    (is (= {:rank :jack :suit :spade}
           (winner [{:rank 10 :suit :spade}
                    {:rank :jack :suit :spade}])
           )))
  (testing "queens are higher rank than jacks"
    (is (= {:rank :queen :suit :spade}
           (winner [{:rank :queen :suit :spade}
                    {:rank :jack :suit :spade}])
           )))
  (testing "kings are higher rank than queens"
    (is (= {:rank :king :suit :spade}
           (winner [{:rank :queen :suit :spade}
                    {:rank :king :suit :spade}])
           )))
  (testing "aces are higher rank than kings"
    (is (= {:rank :ace :suit :spade}
           (winner [{:rank :king :suit :spade}
                    {:rank :ace :suit :spade}])
           )))
  (testing "if the ranks are equal, clubs beat spades"
    (is (= {:rank :ace :suit :club}
           (winner [{:rank :ace :suit :club}
                    {:rank :ace :suit :spade}])
           )))
  (testing "if the ranks are equal, diamonds beat clubs"
    (is (= {:rank :ace :suit :diamond}
           (winner [{:rank :ace :suit :club}
                    {:rank :ace :suit :diamond}])
           )))
  (testing "if the ranks are equal, hearts beat diamonds"
    (is (= {:rank :ace :suit :heart}
           (winner [{:rank :ace :suit :heart}
                    {:rank :ace :suit :diamond}])
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
