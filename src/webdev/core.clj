(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]))

(defn greet [req]
  {:status 200
   :body "Hello, World!"
   :headers {}})

(defn adieu [req]
  {:status 200
   :body "Goodbye, Cruel World!"
   :headers {}})

(defn about [req]
  {:status 200
   :body "Written by O-I under the guidance of Eric Normand and his LispCast on web development in Clojure."
   :headers {}})

(defroutes app
  (GET "/"        [] greet)
  (GET "/goodbye" [] adieu)
  (GET "/about"   [] about)
  (not-found "Page not found."))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
