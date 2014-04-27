(ns flower-server.comms
  (:use clojure.java.io))

(def boards {
             :A [:flower :flower :flower :flower :flower :flower
                 :vine :vine :vine :flower]
             :B [:flower :flower :flower :flower :flower :flower
                 :flower :flower :flower :flower]
             :C [:flower :flower :flower :flower  :vine :flower ]
             :D [:flower :flower :flower :flower :flower :flower
                 :flower :flower]
             :E [:flower :flower :vine :vine :vine :vine :vine]
             :F [:flower :flower :flower :vine]
             })

(def writers {

              })