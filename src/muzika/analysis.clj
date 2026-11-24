(ns muzika.analysis
  (:require [muzika.cleanMusic :as clean-music]))
(import '[java.time Instant ZoneId])

(defn top-10-artists [data]
  (->> data
       (group-by :master_metadata_album_artist_name)
       (map #(vec [(first %) (count (second %))]))
       (sort-by second)
       reverse
       (take 10)))

(defn top-10-tracks [data]
  (->> data
       (group-by (juxt :master_metadata_track_name
                       :master_metadata_album_artist_name))
       (map #(vec [(first %) (count (second %))]))
       (sort-by second)
       reverse
       (take 10)))

(defn top-10-albums [data]
  (->> data
       (group-by (juxt :master_metadata_album_album_name
                       :master_metadata_album_artist_name))
       (map #(vec [(first %) (count (second %))]))
       (sort-by second)
       reverse
       (take 10)))

(defn total-hours-listened [data]
  (->> data
       (map :ms_played)
       (reduce +)
       (#(/ % 3600000.0))))

(defn average-listen-time [data]
  (/ (* (total-hours-listened data) 60)
     (count data)))

(defn longest-listen [data]
  (->> data
       (apply max-key :ms_played)))

(defn shortest-listen [data]
  (->> data
       (apply min-key :ms_played)))

(defn listens-by-year [data]
  (->> data
       (group-by #(subs (:ts %) 0 4))
       (map #(vec [(first %) (count (second %))]))
       (sort-by first)
       reverse))

(defn listening-hours-by-year [data]
  (->> data
       (group-by #(subs (:ts %) 0 4))
       (map #(vec[ (first %) (total-hours-listened (second %))]))
       (sort-by first)
       reverse))

(defn listens-by-month [data]
  (->> data
       (group-by #(subs (:ts %) 0 7))
       (map #(vec [(first %) (count (second %))]))
       (sort-by first)
       reverse))

(defn listening-hours-by-month [data]
  (->> data
       (group-by #(subs (:ts %) 0 7))
       (map #(vec [(first %) (total-hours-listened (second %))]))
       (sort-by first)))

(defn listens-by-hour [data]
  (->> data
       (group-by #(subs (:ts %) 11 13))
       (map #(vec [(first %) (count (second %))]))
       (sort-by first)))

(defn get-local-hour [timestamp]
  (-> timestamp
      Instant/parse
      (.atZone (ZoneId/of "Europe/Belgrade"))
      (.getHour)))

(defn listens-by-hour-local [data]
  (->> data
       (group-by #(get-local-hour (:ts %)))
       (map #(vec [(first %) (count (second %))]))
       (sort-by first)))

(defn get-day-of-week [timestamp]
  (-> timestamp
      Instant/parse
      (.atZone (ZoneId/of "Europe/Belgrade"))
      (.getDayOfWeek)))

(defn listens-by-day-of-week [data]
  (->> data
       (group-by #(get-day-of-week (:ts %)))
       (map #(vec [(first %) (count (second %))]))
       (sort-by first)))

(defn listening-hours-by-day-of-week [data]
  (->> data
       (group-by #(get-day-of-week (:ts %)))
       (map #(vec [(first %) (total-hours-listened (second %))]))
       (sort-by first)))