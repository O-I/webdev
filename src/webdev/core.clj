(ns webdev.core
  (:require [webdev.item.model :as items]
            [webdev.item.handler :refer [handle-index-items]])
  (:require [ring.adapter.jetty :as jetty]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :refer [wrap-params]]
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

(defroutes routes
  (GET "/"         [] greet)
  (GET "/goodbye"  [] adieu)
  (GET "/about"    [] about)
  (ANY "/request"  [] handle-dump)
  (GET "/yo/:name" [] yo)
  (GET "/calc/:i/:op/:j" [] calc)
  ; routes for todo app
  (GET "/items"    [] handle-index-items)
  (not-found "Page not found."))

(defn wrap-db [hdlr]
  (fn [req]
    (hdlr (assoc req :webdev/db db))))

(defn wrap-server [hdlr]
  (fn [req]
    (assoc-in (hdlr req) [:headers "Server"] "ljspt")))

(def app
  (wrap-server
   (wrap-db
    (wrap-params
     routes))))

(defn -main [port]
  (items/create-table db)
  (jetty/run-jetty app
                   {:port (Integer. port)}))

(defn -dev-main [port]
  (items/create-table db)
  (jetty/run-jetty (wrap-reload #'app)
                   {:port (Integer. port)}))
