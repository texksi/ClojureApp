(ns core)

(defn total-hours [start end bouns-hours]
  ( + (- end start) bouns-hours))

(defn weekly-hours [mon tue wed thu fri]
  (+ mon tue wed thu fri)
  )

(defn worked-overtime [hours-worked]
  (if (> hours-worked 40)
    (- hours-worked 40)
    0))

(def drivers
  [{:id 1 :name "Marko" :hours 38, :available true}
   {:id 2 :name "Jovan" :hours 45, :available false}
   {:id 3 :name "Ana" :hours 32, :available true}])

(defn find-driver [drivers id]
  (first (filter (fn [driver]
                   (= (:id driver) id))
                 drivers)))

(defn available-drivers [drivers]
  (filter :available drivers))

(defn total-driver-hours [drivers]
  (reduce + (map :hours drivers)))