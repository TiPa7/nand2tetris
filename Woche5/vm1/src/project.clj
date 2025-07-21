(ns project)

(defproject vm1 "0.1.0"
            :main vm1  ; Namespace mit deiner -main-Funktion
            :aot [vm1] ; Ahead-of-Time-Kompilierung für Java-Export
            :dependencies [[org.clojure/clojure "1.11.1"]])
