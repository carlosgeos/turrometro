(ns turrometro.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [jutsu.core :as j]))

(def data-file (io/resource "chat.txt"))
(def msg-list (line-seq (io/reader data-file)))

(defn capture-name
  "Gets the name from the string passed as parameter. This string is
  supposed to contain name + message in a specific format (see regex)"
  [line]
  (second (re-find #"\d{2}\/\d{2}\/\d{4}, \d{2}:\d{2} - (.*?):" line)))

(println "\n\n")

(println
 (frequencies (map capture-name msg-list)))

(j/graph! "Bar Chart"
          [{:x ["foo" "bar" "foobar"]
            :y [20 30 40]
            :type "bar"}])

(j/start-jutsu!)



(defn -main []
  (println (line-seq data-file)))
