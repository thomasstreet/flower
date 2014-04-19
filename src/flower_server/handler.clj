(ns flower-server.handler
  (:use compojure.core)
  (:use flower-server.flowers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]))

(def speeds (repeatedly 8 #(atom 0)))

(start-ticking (apply (partial map vector) (map positions speeds)))

(defroutes app-routes
  (GET "/" [] (response/redirect "/index.html"))
  (PUT "/:pin/:rpms" [pin rpms]
       (do
         (reset! (nth speeds (read-string pin)) (read-string rpms))
         "ok"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))