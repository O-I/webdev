(defproject webdev "0.1.0-SNAPSHOT"
  :description "ljspt: a sample todo app in Clojure"
  :url "https://ljspt.herokuapp.com"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0-alpha5"]
                 [org.clojure/clojurescript "1.7.48"]
                 [ring "1.4.0"]
                 [compojure "1.4.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [postgresql/postgresql "9.1-901-1.jdbc4"]]
  :min-lein-version "2.0.0"
  :uberjar-name "webdev.jar"
  :main webdev.core
  :profiles {:dev
             {:main webdev.core/-dev-main}})
