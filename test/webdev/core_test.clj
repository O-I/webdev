(ns webdev.core-test
  (:require [clojure.test :refer :all]
            [webdev.core :refer :all]))

(defn request [resource test-app & params]
  (test-app {:request-method :get :uri resource :params (first params)}))

(deftest test-routes
  (is (= 404 (:status (request "/path-to-nowhere" app))))
  (is (= "Page not found." (:body (request "/path-to-nowhere" app))))
  (is (= 200 (:status (request "/" app))))
  (is (= "Hello, World!" (:body (request "/" app)))))