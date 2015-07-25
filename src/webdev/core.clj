(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]))

(defn greet [{:keys [uri] :as req}]
  (if (= uri "/")
    {:status 200
     :body "Hello, World!"
     :headers {}}
    {:status 404
     :body "Not Found"
     :headers {}}))

(defn -main [port]
  (jetty/run-jetty greet
                   {:port (Integer. port)}))
