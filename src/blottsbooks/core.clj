(ns blottsbooks.core
  (:gen-class))

(defn say-welcome [what]
  (println "Welcome to" (str what "!")))

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


(defn pet-dispatcher [pet]
  (cond
    (= (pet :type) "Dog") :dog
    (= (pet :type) "Cat") :cat
    :else :others)
  )

(defmulti pet-sound pet-dispatcher)
(defmethod pet-sound :dog [pet]
  (println (pet :name) "barks!"))

(defmethod pet-sound :cat [pet]
  (println (pet :name) "mews!"))

(defmethod pet-sound :others [pet]
  (println (pet :name) "screams!"))


(defn sum-copies [books]
  (loop [books books total 0]
    (if (empty? books) total
                       (recur (rest books) (+ total (:copies-sold (first books)))))))

(defn factorial-tail-recur [number]
  (loop [number number fact 1N]
    (if (= number 1) fact
                     (recur (- number 1) (* fact number)))))

(defn factorial
  ([number] (factorial number 1N))
  ([number fact]
   (if (<= number 1) fact
                     (recur (- number 1) (* fact number)))))

(defn factorial-apply
  [number]
  (let [x (vec (range 1N (+ number 1)))]
    (apply * x)))

(defn fibo
  [n]
  (cond (= n 0) 1
        (= n 1) 1
        :else (+ (fibo (- n 2)) (fibo (- n 1))))
  )

(defn fibo-num
  [n]
  (loop [current 0N next 1N index 0]
    (if (= index n)
      current
      (recur next (+ current next) (inc index)))))

(defn create-vector
  ([a] (create-vector 0 a))
  ([a b] (create-vector a b []))
  ([a b vec]
   (if (< b a) vec
               (recur (+ a 1) b (conj vec a))))
  )

(defn print-book
  [book]
  (println book))
(defn ship-book
  [book]
  (ship-and-greet false (:price book)))
(defn publish-book [book]
  {:pre  [(:title book) (:author book)]
   :post [(float? %)]}
  (print-book book)
  (ship-book book))

(defn average
  [& numbers]
  (/ (apply + numbers) (count numbers)))
(defn cheap?
  [book]
  (when (< (:price book) 2) book))

(defn cheaper-than [max-price book]
  (when (<= (:price book) max-price) book))
(def real-cheap? (partial cheaper-than 1.99))
(def somewhat-cheap? (partial cheaper-than 2.99))


(def sum-nums
  (fn [& nums]
    (loop [n nums total 0]
      (if (empty? n) total
                     (recur (rest n) (+ total (first n)))))))

(defn compute-discount-amount [amount discount-percent min-charge]
  (let [discount-amount (* amount (- 1.0 discount-percent))]
    (if (>= discount-amount min-charge) discount-amount min-charge))
  )

(def books [{:name "The adventure of sherlock holmes", :author "Doyle"} {:name "Midnight's Children", :author "Rushdie"}])
(map #(if (contains? % :name)
        (update-in % [:name] clojure.string/upper-case)) books)
(defn format-books [books]
  (->>
    books
    (sort-by :name)
    reverse
    (take 2)
    (map :name)
    (interpose "//")
    (apply str)))
(def ^:dynamic *debug-enabled* false)
(defn debug [msg]
  (if *debug-enabled*
    (print msg)))
(binding [*debug-enabled* true]
  (debug "Hello World")
  )
(defn -main []
  (say-welcome "BlottsBooks"))
