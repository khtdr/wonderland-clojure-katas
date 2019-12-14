(ns wonderland-number.finder)

(defn wonderland-number []
  (->>
   (range 100000 1000000)
   (filter (fn [n]
             (let [n2 (set (str (* 2 n)))
                   n3 (set (str (* 3 n)))]
               (if (= n2 n3)
                 (let [n4 (set (str (* 4 n)))]
                   (if (= n3 n4)
                     (let [n5 (set (str (* 5 n)))]
                       (if (= n4 n5)
                         (let [n6 (set (str (* 6 n)))]
                           (= n5 n6))))))))))
   (first)))

(defn wonderland-number-readable []
  (->>
   (range 100000 1000000)
   (filter (fn [n]
             (let [n2 (set (str (* 2 n)))
                   n3 (set (str (* 3 n)))
                   n4 (set (str (* 4 n)))
                   n5 (set (str (* 5 n)))
                   n6 (set (str (* 6 n)))]
               (= n2 n3 n4 n5 n6))))
   (first)))

(time (wonderland-number))
(time (wonderland-number-readable))
