(ns flower-server.handler
  (:use compojure.core)
  (:use flower-server.positions)
  (:use flower-server.comms)
  (:use flower-server.flowers)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as response]
            [clojure.data.json :as json]))

(def speeds (repeatedly 8 #(atom 0)))

(defn transpose [x] (apply (partial map vector) x))


(quote (map (fn [x]
       (start-ticking (writers x)
                      (transpose [(flower) (flower) (flower) (flower)
                                  (flower)   (flower) (flower) (flower)])))
     [:A :B :C] )) 

(start-ticking (writers :C)
               (transpose [(flower) (flower) (flower) (flower)
                           (flower)   (flower) (flower) (flower)]))


(def seconds (* 12 60 60))

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
         :flower5 (take seconds (flower))
         :flower6 (take seconds (flower))
         :vine1   (take seconds (vine))
         :vine2   (take seconds (vine))
         :vine3   (take seconds (vine))
         

         

         }))
  
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))