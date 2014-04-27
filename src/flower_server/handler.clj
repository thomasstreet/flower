(ns flower-server.handler
  (:use compojure.core)
  (:use flower-server.positions)
  (:use flower-server.flowers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [clojure.data.json :as json]))

(def speeds (repeatedly 8 #(atom 0)))

(start-ticking (apply (partial map vector) (map positions speeds)))

(def seconds (* 24 60 60))

(defroutes app-routes
  (GET "/" [] (response/redirect "/index.html"))
  (PUT "/:pin/:rpms" [pin rpms]
       (do
         (reset! (nth speeds (read-string pin)) (read-string rpms))
         "ok"))
  
  (GET "/fns" []
       (json/write-str
        {:flower1 (take seconds (flower))
         :flower2 (take seconds (flower))
         :flower3 (take seconds (flower))
         :flower4 (take seconds (flower))

         }))
  
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))