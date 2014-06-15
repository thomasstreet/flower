(ns flower-server.positions)

(def transition-time (* 60 5))
(def vine-transition-time 10)

(def open-min (* 40 60))
(def open-max (* 60 60))

(def closed-min (* 20 60))
(def closed-max (* 60 60))

(def vine-rest-min (* 5 60))
(def vine-rest-max (* 15 60))




(defn flower-cycle
  []
  (letfn [(open []
            (reductions + (repeat transition-time (/ 1 transition-time))))

          (close []
            (reductions + 1 (repeat transition-time (/ -1 transition-time))))

          (restOpen []
            (repeat (+ open-min (rand-int (- open-max open-min))) 1))

          (restClosed []
            (repeat (+ closed-min (rand-int (- closed-max closed-min))) 0))]
          
    (map #(apply % []) (cycle [open restOpen close restClosed]))))

(defn vine-cycle
  []
  (letfn [(open []
            (reductions + (repeat vine-transition-time (/ 1 vine-transition-time))))
          (close []
            (reductions + 1 (repeat vine-transition-time (/ -1 vine-transition-time))))
          (restClosed []
            (repeat (+ vine-rest-min (rand-int (- vine-rest-max closed-min))) 0))]
    (map #(apply % []) (cycle [open close restClosed]))))
    

(defn pop-first [x]
  "[[1 2] [3 4]] => [[2] [3 4]]
   [[2] [3 4]] => [[3 4]]"
  (if (first (rest (first x)))
    (cons (rest (first x)) (rest x))
    (next x)))

(defn cat
  "Concat that takes a lazy seq of lazy seqs"
  [c]
  (map #(first (first %)) (iterate pop-first c)))

(defn flower
  []
  (cat (drop (rand-int 3) (flower-cycle))))

(defn vine
  []
  (cat (drop (rand-int 2) (vine-cycle))))
    
  