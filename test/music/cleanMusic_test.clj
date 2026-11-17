(ns music.cleanMusic-test
  (:require [midje.sweet :refer [fact]]
            [muzika.cleanMusic :as clean-music]))

(def test-data
  [{:master_metadata_track_name "Starboy"
    :master_metadata_album_artist_name "The Weeknd"
    :ms_played 1000
    :ts nil}

   {:master_metadata_track_name nil
    :master_metadata_album_artist_name "Drake"
    :ms_played 40000
    :ts 100}

   {:master_metadata_track_name "505"
    :master_metadata_album_artist_name "Arctic Monkeys"
    :ms_played 50000
    :ts 100}

   {:master_metadata_track_name "Song"
    :master_metadata_album_artist_name nil
    :ms_played 20
    :ts 1000}])

(def ms-played-test
  [{:ms_played 29999}
   {:ms_played 30000}
   {:ms_played 30001}])

(def invalid-duration-test
  [{:ms_played 50000}
   {:ms_played -100}
   {:ms_played nil}
   {:ms_played "30000"}
   {:ms_played 0}])

(fact "Filter songs without missing data"
     (count (clean-music/filter-songs test-data)) => 2)

(fact "Filter songs with no data"
      (clean-music/filter-songs []) => [])

(fact "Songs with complete information"
      (->> test-data
           clean-music/filter-songs
          (map :master_metadata_track_name)
           vec) => ["Starboy" "505"])

(fact "Count short plays"
      (count (clean-music/filter-short-plays test-data)) => 2)

(fact "Filter short plays"
      (->> test-data
           clean-music/filter-short-plays
           (map :ms_played)
           vec) => [1000,20])

(fact "Count short plays - border value"
      (->> ms-played-test
           clean-music/filter-short-plays
           (map :ms_played)
           vec) => [29999])

(fact "Count long plays"
      (count (clean-music/filter-long-plays test-data)) => 2)

(fact "Filter long plays"
      (->> test-data
           clean-music/filter-long-plays
           (map :ms_played)
           vec) => [40000,50000])

(fact "Count long plays - border value"
      (->> ms-played-test
           clean-music/filter-long-plays
           (map :ms_played)
           vec) => [30000,30001])

(fact "Count clean songs"
      (count (clean-music/clean-data test-data)) => 1)

(fact "Filter clean songs"
      (->> test-data
           clean-music/clean-data
           (map :master_metadata_track_name)
           vec) => ["505"])

(fact "Count missing timestamps"
      (clean-music/count-missing-timestamps test-data) => 1)

(fact "Count invalid durations"
      (->> invalid-duration-test
           clean-music/count-invalid-durations) => 3)

