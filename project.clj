(defproject flower-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [org.rxtx/rxtx "2.1.7"]
                 [incanter "1.5.5"]
                 [org.clojure/data.json "0.2.4"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler flower-server.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})
