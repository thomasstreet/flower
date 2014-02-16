(ns flower-server.handler
  (:use compojure.core)
  (:use serial-port)
  (:use flower-server.flowers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]))

(def speeds (repeatedly 6 #(atom 0)))

(def port (open "/dev/tty.usbmodem1411"))
(on-byte port #(prn (char %)))

(start-ticking port (apply (partial map vector) (map positions speeds)))

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