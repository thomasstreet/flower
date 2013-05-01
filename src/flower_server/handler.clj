(ns flower-server.handler
  (:use compojure.core)
  (:use flower-server.flowers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]))

(def speed (atom 40))
(try
  (def board (init-board))
  (def flower (future (tick (partial move-to board) (positions speed) 1)))
  (catch Exception e))



(defroutes app-routes
  (GET "/set/:rpms" [rpms]
       (do
         (reset! speed (read-string rpms))
         (str "New speed: " rpms)))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
