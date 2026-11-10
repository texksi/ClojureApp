(ns muzika.loadMusic
   (:require [clojure.data.json :as json]
             [clojure.java.io :as io]))

(def folder-path "musicData")

(defn load-file [file-path]
  (with-open [r (io/reader file-path)]
    (json/read r :key-fn keyword)))

(defn load-all-files [folder-path]
  (let [files (.listFiles (io/file folder-path))]
    (vec (mapcat load-file
                 (filter #(.endsWith (.getName %) ".json") files)))))

(def raw-data (load-all-files folder-path))