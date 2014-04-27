(ns flower-server.positions)

(def transition-time (* 60 5))

(def open-min (* 20 60))
(def open-max (* 120 60))

(def closed-min (* 5 60))
(def closed-max (* 20 60))


(defn open
  []
  (reductions + (repeat transition-time (/ 1 transition-time))))

(defn close
  []
  (reductions + 1 (repeat transition-time (/ -1 transition-time))))

(defn restOpen
  []
  (repeat (+ open-min (rand-int (- open-max open-min))) 1))

(defn restClosed
  []
  (repeat (+ closed-min (rand-int (- closed-max closed-min))) 0))

(defn flower-cycle
  []
  (map #(apply % []) (cycle [open restOpen close restClosed])))


(defn pop-first [x]
  (if (first (rest (first x)))
    (cons (rest (first x)) (rest x))
    (next x)))

(defn cat
  [c]
  (map #(first (first %)) (iterate pop-first c)))

(defn flower
  []
  (cat (drop (rand-int 3) (flower-cycle))))
    
  