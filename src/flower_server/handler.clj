(ns flower-server.handler
  (:use compojure.core)
  (:use flower-server.flowers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]))

(def speeds (repeatedly 8 #(atom 0)))

(defn start-ticking
  [board nth_pin]
  (future
    (tick
     (partial move-to board (+ 2 nth_pin)) (positions (nth speeds nth_pin)))))

(do
  (def board (init-board))
  (def flowers
    (map (partial start-ticking board) (range 8))))

(defroutes app-routes
  (GET "/" [] (response/redirect "/index.html"))
  (PUT "/:pin/:rpms" [pin rpms]
       (do
         (prn (str "New speed for motor" (read-string pin) " : " (read-string rpms)))
         (reset! (nth speeds (read-string pin)) (read-string rpms))
         (prn "speed atoms" (map deref speeds))
         "ok"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
