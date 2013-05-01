(defproject flower-server "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [clodiuno "0.0.3-SNAPSHOT"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler flower-server.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}})