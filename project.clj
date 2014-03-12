(defproject iplant-api "0.1.0-SNAPSHOT"
  :description "Stub api definition for iPlant"
  :url "http://example.com/FIXME"
  :license {:name "BSD Standard License"
            :url "http://www.iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [metosin/compojure-api "0.13.1"]
                 [metosin/ring-http-response "0.4.0"]
                 [metosin/ring-swagger-ui "2.0.16-2"]
                 [clj-time "0.7.0"]
                 [liberator "0.11.0"]]
  :ring {:handler iplant-api.core/iplant}
  :uberjar-name "iplant-api.jar"
  :profiles {:uberjar {:resource-paths ["swagger-ui"]}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]]
                   :plugins [[lein-ring "0.8.11"]]}})

