(ns flower-server.flowers
  (:use :reload-all clodiuno.core)
  (:use :reload-all clodiuno.firmata))

(defn steps
  [speed-atom]
  (repeatedly #(-> @speed-atom (/ 60000) (* 360))))

(def distances (comp (partial reductions + 0) steps))

(defn fold
  [position]
  (if (< position 180)
    position
    (+ 180 (- 180 position))))

(def positions (comp (partial map #(-> % (mod 360) (fold) )) distances))

(defn tick
  [f seq ms]
  (doseq [x seq]
    (Thread/sleep ms)
    (f x)))

(def servo-pins (range 2 10))

(defn move-to
  [board pin pos]
  (analog-write board pin (int pos)))

(defn init-board []
  (let [board (arduino :firmata "/dev/tty.usbserial-A6008j80")]
    (doseq [pin servo-pins]
      (pin-mode board pin SERVO))
    board))

