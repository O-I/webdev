(ns webdev.core
  (:require [webdev.item.model :as items])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [compojure.core :refer [defroutes ANY GET POST PUT DELETE]]
            [compojure.route :refer [not-found]]
            [ring.handler.dump :refer [handle-dump]]))

(def db "jdbc:postgresql://localhost/webdev")

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

(def op-str
  {"+" +
   "-" -
   "*" *
   ":" /})

(defn calc [req]
  (let [i  (Integer. (get-in req [:route-params :i]))
        j  (Integer. (get-in req [:route-params :j]))
        op (get-in req [:route-params :op])
        f  (get op-str op)]
    (if f
      {:status 200
       :body (str (f i j))
       :headers {}}
      {:status 404
       :body (str "Unknown operator: " op)
       :headers {}})))

(defroutes app
  (GET "/"         [] greet)
  (GET "/goodbye"  [] adieu)
  (GET "/about"    [] about)
  (GET "/request"  [] handle-dump)
  (GET "/yo/:name" [] yo)
  (GET "/calc/:i/:op/:j" [] calc)
  (not-found "Page not found."))

(defn -main [port]
  (items/create-table db)
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (items/create-table db)
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
