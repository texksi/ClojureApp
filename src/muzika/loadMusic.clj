(ns muzika.loadMusic
   (:require [clojure.data.json :as json]
             [clojure.java.io :as io]))

(def folder-path "musicData")

(defn load-json-file [file-path]
  (with-open [r (io/reader file-path)]
    (json/read r :key-fn keyword)))

(defn load-all-files [folder-path]
  (let [files (.listFiles (io/file folder-path))]
    (vec (mapcat load-json-file
                 (filter #(.endsWith (.getName %) ".json") files)))))

(def raw-data (load-all-files folder-path))

(defn count-audio-records [folder-path]
  (->> (.listFiles (io/file folder-path))
       (filter #(and (.startsWith (.getName %) "Streaming_History_Audio_")
                     (.endsWith (.getName %) ".json")))
       (mapcat load-json-file)
       count))

(defn count-video-records [folder-path]
  (->> (.listFiles (io/file folder-path))
       (filter #(and (.startsWith (.getName %) "Streaming_History_Video_")
                     (.endsWith (.getName %) ".json")))
       (mapcat load-json-file)
       count))