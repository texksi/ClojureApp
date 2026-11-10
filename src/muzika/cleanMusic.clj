(ns muzika.cleanMusic)

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