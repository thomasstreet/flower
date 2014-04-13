(ns flower-server.flowers
  (:use serial-port)
  (:use :reload-all clodiuno.firmata))

(defn steps
  [speed-atom]
  (repeatedly #(-> @speed-atom (/ 60000) (* 720))))

(def distances (comp (partial reductions + 0) steps))

(defn fold
  [position]
  (if (< position 180)
    position
    (+ 180 (- 180 position))))

(def positions (comp (partial map #(-> % (mod 360) (fold))) distances))

(defn tick
  [f seq ms]
  (doseq [x seq]
    (Thread/sleep ms)
    (f x)))

(def servo-pins (range 2 10))

(defn indexed
  [coll]
  (map vector (iterate inc 0) coll))

(defn move-all
  [port position-vector]
  (let [message (str "["
                     (clojure.string/join "," (concat (map int position-vector)))
                     "]"
                     )
        message-as-bytes (byte-array (map byte message))]

    (write port message-as-bytes)))

(defn start-ticking
  [port position-vector]
  (future
    (tick
     (partial move-all port)
     position-vector
     10)))



