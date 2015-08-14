(ns webdev.item.view
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [html h]]))

(defn items-page [items]
  (html5 {:lang :en}
         [:head
          [:title "Ljspt"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:link {:href "/bootstrap/css/bootstrap.min.css"
                  :rel :stylesheet}]]
         [:body
          [:div.container
           [:h1 "Items"]
           [:div.row
            (if (seq items)
              [:table.table.table-striped
               [:thead
                [:tr
                 [:th "Name"]
                 [:th "Description"]]]
               [:tbody
                (for [i items]
                  [:tr
                   [:th (:name i)]
                   [:th (:description i)]])]]
              [:div.col-sm-offset-1 "There are no new items."])]]
          [:script {:src "http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"}]
          [:script {:src "/bootstrap/js/bootstrap.min.js"}]]))