(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]))

(defn greet [{:keys [uri] :as req}]
  (if (= uri "/")
    {:status 200
     :body "Hello, World! - sans reloading!"
     :headers {}}
    {:status 404
     :body "Not Found"
     :headers {}}))

(defn -main [port]
  (jetty/run-jetty greet
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (jetty/run-jetty (wrap-reload #'greet)
                   {:port (Integer. port)}))
