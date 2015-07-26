(ns webdev.core
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes GET]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))

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

(defn yo [{:keys [:route-params]}]
  {:status 200
   :body (str "Yo, " (:name route-params) "!" )
   :headers {}})

(defn calc [req]
  (let [int1   (Integer. (get-in req [:route-params :int1]))
        int2   (Integer. (get-in req [:route-params :int2]))
        op-str (get-in req [:route-params :op])
        op     (-> (if (= op-str ":") "/" op-str)
                   symbol resolve)]
    {:status 200
     :body (str (op int1 int2))
     :headers {}}
    ))

(defroutes app
  (GET "/"         [] greet)
  (GET "/goodbye"  [] adieu)
  (GET "/about"    [] about)
  (GET "/request"  [] handle-dump)
  (GET "/yo/:name" [] yo)
  (GET "/calc/:int1/:op/:int2" [] calc)
  (not-found "Page not found."))

(defn -main [port]
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
