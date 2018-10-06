(ns blottsbooks.core
  (:gen-class))

(defn say-welcome [what]
  (println "Welcome to" what "!"))

(defn print-greetings [preferred-customer]
  (if preferred-customer
    (println "Welcome back to Blotts Books.")
    (println "Welcome to Blotts Books")))

(defn shipping-charges [preferred-customer order-amount]
  (if preferred-customer
    0.0
    (* order-amount 0.10)))

(defn ship-and-greet [preferred-customer order-amount]
  (cond preferred-customer
        (do
          (println "Welcome back to Blotts books.")
          0.0)
        (< order-amount 5.0)
        (do
          (println "Welcome to Blotts books.")
          0.6)
        :else
        (do
          (println "Welcome to Blotts books.")
          (* order-amount 0.10))
        ))

(defn dispatch-book-format [book]
  (cond
    (vector? book) :vector-book
    (contains? book :title) :standard-map
    (contains? book :book) :alternative-map))

(defmulti normalize-books dispatch-book-format)
(defmethod normalize-books :vector-book [book]
  {:title (first book) :author (second book)})

(defmethod normalize-books :standard-map [book]
  book)

(defmethod normalize-books :alternative-map [book]
  {:title (:book book) :author (:by book)})
(defn -main []
  (say-welcome "BlottsBooks"))
