(ns flower-server.handler
  (:use compojure.core)
  (:use flower-server.flowers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]))

(def speed (atom 0))
(do
  (def board (init-board))
  (def flower (future (tick (partial move-to board) (positions speed) 1))))



(defroutes app-routes
  (GET "/" [] (response/redirect "/index.html"))
  (PUT "/set/:rpms" [rpms]
       (do
         (prn (str "New speed: " (read-string rpms)))
         (reset! speed (read-string rpms))
         "ok"))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))
