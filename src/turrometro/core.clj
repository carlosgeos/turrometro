(ns turrometro.core
  (:require [clojure.java.io :as io]
            [jutsu.core :as j]))

(def FILE_NAME "chat.txt")

(defn capture-name
  "Gets the name from the string passed as parameter. This string is
  supposed to contain name + message in a specific format (see regex).

  The second element is taken because the first one is the full match
  and the second is the first capturing group (the name)"
  [line]
  (second (re-find #"\d{2}\/\d{2}\/\d{4}, \d{2}:\d{2} - (.*?):" line)))

(defn calc-freqs
  "Get a sorted map with the names and messages of everyone in the
  conversation"
  []
  (let [data-file (io/resource FILE_NAME)
        msg-list (line-seq (io/reader data-file))]
    (sort-by val > (frequencies (map capture-name msg-list)))))

(defn plot-bar-graph
  "Starts the server and creates the plot"
  [freqs]
  (j/start-jutsu!)

  ;; doc says Jutsu needs some time...
  (Thread/sleep 3000)

  ;; Parameters for j/graph! are ID, data and layout
  (j/graph! "Turrometro"
            [{:x (keys freqs)
              :y (vals freqs)
              :type "bar"}]
            ;; layout
            {:xaxis {:title "Names"}
             :yaxis {:title "# of messages"}
             :title "# of messages / people"
             :paper_bgcolor "#eee"
             :plot_bgcolor "#eee"
             :margin {:b 140}}))


(defn -main []
  (plot-bar-graph (calc-freqs)))
