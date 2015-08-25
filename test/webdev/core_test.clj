(ns webdev.core-test
  (:require [clojure.test :refer :all]
            [webdev.core :refer :all]))

(defn request [resource test-app & params]
  (test-app {:request-method :get :uri resource :params (first params)}))

(deftest test-routes
  (is (= 404 (:status (request "/path-to-nowhere" app))))
  (is (= "Page not found." (:body (request "/path-to-nowhere" app))))
  (is (= 200 (:status (request "/" app))))
  (is (= "Hello, World!" (:body (request "/" app))))
  (is (= 200 (:status (request "/greet" app))))
  (is (= "Hello, World!" (:body (request "/greet" app))))
  (is (= 200 (:status (request "/adieu" app))))
  (is (= "Goodbye, Cruel World!" (:body (request "/adieu" app))))
  (is (= 200 (:status (request "/about" app))))
  (is (= "Written by O-I under the guidance of Eric Normand and his LispCast on web development in Clojure." (:body (request "/about" app))))
  (is (= 200 (:status (request "/adieu" app))))
  (is (= "Goodbye, Cruel World!" (:body (request "/adieu" app)))))