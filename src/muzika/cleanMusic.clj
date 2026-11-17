(ns muzika.cleanMusic
  (:require [muzika.loadMusic :as music]))

(defn filter-songs [data]
  (let [songs (filter
                #(and (not= (:master_metadata_track_name %) nil)
                      (not= (:master_metadata_album_artist_name %) nil))
                data)]
    songs))

(defn filter-short-plays [data]
  (let [short-plays (filter #(< (:ms_played %) 30000)
                            data)]
    short-plays))

(defn filter-long-plays [data]
  (let [long-plays (filter #(>= (:ms_played %) 30000)
                           data)]
    long-plays))

(defn clean-data [data]
  (->> data
       filter-songs
       filter-long-plays))

(def cleaned-data (vec (clean-data music/raw-data)))

(defn count-invalid-durations [data]
  (->> data
       (filter #(or (not (number? (:ms_played %)))
                    (and (number? (:ms_played %))
                         (neg? (:ms_played %)))))
       count))

(defn count-duplicate [data]
  (let [total (count data)
        unique (count (distinct data))]
    (- total unique)))