(ns blottsbooks.core-test
  (:require [clojure.test :refer :all]
            [blottsbooks.core :as bc]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))))

(deftest price-test
  (testing "Testing the price"
    (is (= (bc/compute-discount-amount 100 0.3 10) 70.0))))