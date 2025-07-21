(ns project)

(defproject assembler "0.1.0"
            :main assembler  ; Namespace mit deiner -main-Funktion
            :aot [assembler] ; Ahead-of-Time-Kompilierung für Java-Export
            :dependencies [[org.clojure/clojure "1.11.1"]])
