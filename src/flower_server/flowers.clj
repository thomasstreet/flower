(ns flower-server.flowers)

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





(defn format-message
  [v]
  (str "["
        (clojure.string/join "," (concat (map #(int (* 180 %)) v)))
        "]"))

(defn start-ticking
  [write-to position-vector]
  (println (str "ticking to " write-to))
  (future
    (tick
     (fn [m]
       (binding [*out* write-to]

         (println (format-message m))))
     position-vector
     1000)))



