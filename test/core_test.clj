(ns core-test
  (:require [midje.sweet :refer :all]
            [core :as core]))

(fact "Total hours of driver in a day"
      (core/total-hours 8 16 2) => 10)

(fact "Total hours of driver in a week"
      (core/weekly-hours 8 6 8 7 8) => 37)

(fact "Overtime work"
      (core/worked-overtime 45) => 5
      (core/worked-overtime 38) => 0)

(fact "Find driver by ID"
      (core/find-driver core/drivers 1) =>   {:id 1 :name "Marko" :hours 38 :available true})

(fact "Find available drviers"
      (core/available-drivers core/drivers) => '({:id 1 :name "Marko" :hours 38 :available true}  {:id 3 :name "Ana" :hours 32, :available true}) )

(fact "Total hours by drivers"
      (core/total-driver-hours core/drivers) => 115)
