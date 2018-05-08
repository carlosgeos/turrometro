(ns turrometro.core
  (:require [clojure.java.io :as io])
  (:use [plotly-clj.core]))

(offline-init)

(def FILE_NAME "chat.txt")
(def OUT_GRAPH_FILE_NAME "turrometro.html")

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
  "Creates the plot in an .html file and saves it"
  [freqs]
  (-> (plotly {:x (keys freqs)})
      (add-bar
       :x :x
       :y (vals freqs)
       :name "names / # messages")
      (set-layout :title "Turrometro"
                  :xaxis {:title "Names"}
                  :yaxis {:title "# of messages"}
                  :paper_bgcolor "#eee"
                  :plot_bgcolor "#eee"
                  :margin {:b 140})
      (save-html OUT_GRAPH_FILE_NAME)))


(defn -main []
  (plot-bar-graph (calc-freqs)))
