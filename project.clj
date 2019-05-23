(defproject turrometro "0.1.0-SNAPSHOT"
  :description "App para ver quien da más la turra en un grupo de whatsapp"
  :url "https://github.com/carlosgeos/turrometro"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main turrometro.core
  :profiles {:uberjar {:aot [turrometro.core]}}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [plotly-clj "0.1.1"]])
