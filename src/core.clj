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

(def driver
  {:name "Marko"
   :bus "Autobus 12"
   :hours 38})