(ns project)

(defproject vm2 "0.1.0"
            :main vm2  ; Namespace mit deiner -main-Funktion
            :aot [vm2] ; Ahead-of-Time-Kompilierung für Java-Export
            :dependencies [[org.clojure/clojure "1.11.1"]])
