(ns vm2
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [clojure.math.combinatorics :as combo]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; READING THE FILE
;(defn read-file
;  "Reads file, removes comments, removes whitespaces"
;  [filepath]
;  (with-open [rdr (clojure.java.io/reader filepath)]
;    (into [] (filter
;               (fn [x] (not (empty? x)))
;               (map
;                 #(clojure.string/replace %1 #"(//.*)" "")
;                 (map
;                   #(clojure.string/replace %1 #"\t*" "")
;                   (reduce
;                     conj
;                     []
;                     (line-seq rdr))))))))
(defn read-file
  "Reads file, removes comments, trims whitespace, removes tabs and blank lines"
  [filepath]
  (with-open [rdr (clojure.java.io/reader filepath)]
    (into []
          (comp
            (map #(clojure.string/replace % #"//.*" "")) ; Kommentare entfernen
            (map #(clojure.string/replace % #"\t" ""))   ; Tabs entfernen
            (map clojure.string/trim)                    ; führende und folgende Leerzeichen entfernen
            (remove clojure.string/blank?))              ; leere Zeilen entfernen
          (line-seq rdr))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; PART 1: VM1 (last project)

;; PART 1.1: ARITHMETIC AND LOGICAL STACK COMMANDS

(def add-assembler ["@SP", "AM=M-1", "D=M", "A=A-1", "M=M+D"])

(def sub-assembler ["@SP", "AM=M-1", "D=M", "A=A-1", "M=M-D"])

(def neg-assembler ["@SP", "A=M-1", "M=-M"])

(defn eq-assembler []
  (let [unique (java.util.UUID/randomUUID)]
    ["@SP"
     "AM=M-1"
     "D=M"
     "A=A-1"
     "D=M-D"
     (str "@EQ_TRUE$" unique)
     "D;JEQ"
     (str "(EQ_FALSE$" unique ")")
     "@0"
     "D=A"
     "@SP"
     "A=M-1"
     "M=D"
     (str "@END$" unique)
     "0;JMP"
     (str "(EQ_TRUE$" unique ")")
     "D=-1"
     "@SP"
     "A=M-1"
     "M=D"
     (str "(END$" unique ")")]))

(defn gt-assembler []
  (let [unique (java.util.UUID/randomUUID)]
    ["@SP"
     "AM=M-1"
     "D=M"
     "A=A-1"
     "D=M-D"
     (str "@GT_TRUE$" unique)
     "D;JGT"
     (str "(GT_FALSE$" unique ")")
     "@0"
     "D=A"
     "@SP"
     "A=M-1"
     "M=D"
     (str "@END$" unique)
     "0;JMP"
     (str "(GT_TRUE$" unique ")")
     "D=-1"
     "@SP"
     "A=M-1"
     "M=D"
     (str "(END$" unique ")")]))

(defn lt-assembler []
  (let [unique (java.util.UUID/randomUUID)]

    ["@SP"
     "AM=M-1"
     "D=M"
     "A=A-1"
     "D=M-D"
     (str "@LT_TRUE$" unique)
     "D;JLT"
     (str "(LT_FALSE$" unique ")")
     "@0"
     "D=A"
     "@SP"
     "A=M-1"
     "M=D"
     (str "@END$" unique)
     "0;JMP"
     (str "(LT_TRUE$" unique ")")
     "D=-1"
     "@SP"
     "A=M-1"
     "M=D"
     (str "(END$" unique ")")]))

(def and-assembler ["@SP"
                    "AM=M-1"
                    "D=M"
                    "A=A-1"
                    "M=D&M"])

(def or-assembler ["@SP"
                   "AM=M-1"
                   "D=M"
                   "A=A-1"
                   "M=D|M"])

(def not-assembler ["@SP"
                    "A=M-1"
                    "M=!M"])

; Returns assembler code for a given line of bytecode

(defn append-comment-to-last [lines comment]
  (let [last-line (last lines)
        modified-last (str last-line " //" comment)]
    (concat (butlast lines) [modified-last])))


(defmulti bytecode-to-assembler (fn [str filename]
                                  (first (clojure.string/split str #" "))))

(defmethod bytecode-to-assembler "add" [str filename] (append-comment-to-last add-assembler str))
(defmethod bytecode-to-assembler "sub" [str filename] (append-comment-to-last sub-assembler str))
(defmethod bytecode-to-assembler "neg" [str filename] (append-comment-to-last neg-assembler str))
(defmethod bytecode-to-assembler "eq" [str filename] (append-comment-to-last (eq-assembler) str))
(defmethod bytecode-to-assembler "gt" [str filename] (append-comment-to-last (gt-assembler) str))
(defmethod bytecode-to-assembler "lt" [str filename] (append-comment-to-last (lt-assembler) str))
(defmethod bytecode-to-assembler "and" [str filename] (append-comment-to-last and-assembler str))
(defmethod bytecode-to-assembler "or" [str filename] (append-comment-to-last or-assembler str))
(defmethod bytecode-to-assembler "not" [str filename] (append-comment-to-last not-assembler str))

;; PART 1.2: MEMORY ACCESS COMMANDS (push & pop)

(def segment-abbreviation {"argument" "ARG"
                           "local"    "LCL"
                           "static"   "16"                  ; we start with new symbols at memory address 16
                           "this"     "THIS"                ; "constant" ommited, see below
                           "that"     "THAT"
                           "pointer"  "3"                   ; push pointer 0 ist für Adresse 3 (also THIS), push pointer 1 für 4 (also THAT)
                           "temp"     "5"}                  ; memory segment temp is [5,..,12]
  )

(defn dynamic-push-assembler-start
  "Returns first part (vector) of assembler code for VM push operation"
  [segment index filename]
  (if (= segment "constant")
    [(str "@" index), "D=A"]                                ;; For constant: RAM[SP] = index, SP++
    (if (or (= segment "pointer") (= segment "temp"))
      [(str "@" (segment-abbreviation segment))             ;; For pointer and temp: RAM[SP] = RAM[segment + index], SP++
       "D=A"
       (str "@" index)
       "A=D+A"
       "D=M"]
      (if (= segment "static")
        [(str "@" filename "." index)                       ;; For static: @XXX.index to individualize names of static variables of different files with possibly same names
         "D=M"
         "@SP"
         "A=M+1"
         "A=A-1"
         "M=D"]
        [(str "@" (segment-abbreviation segment))           ; For all other segments: RAM[SP] = RAM[RAM[segment] + index], SP++
         "D=M"                                              ; ACHTUNG: NICHT D=A!!!
         (str "@" index)
         "A=D+A"
         "D=M"]))
    )
  )


(defn push-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'push segment index'"
  [s filename]
  (let [[op segment index] (clojure.string/split s #" ")
        start (dynamic-push-assembler-start segment index filename)
        other ["@SP" "AM=M+1" "A=A-1" "M=D"]
        all (vec (concat start other))]
    all
    ))

(defmethod bytecode-to-assembler "push" [str filename] (append-comment-to-last (push-to-assembler str filename) str))

(defn dynamic-pop-assembler-start
  "Returns first part (vector) of assembler code for VM pop operation"
  [segment index filename]
  (if (or (= segment "pointer") (= segment "temp"))
    [(str "@" index)
     "D=A"
     (str "@" (segment-abbreviation segment))
     "D=D+A"]                                               ; For pointer and temp: Address = segment + index
    (if (= segment "static")
      [(str "@" filename "." index)                         ; For static: @XXX.index to individualize names of static variables of different files with possibly same names
       "D=A"]
      [(str "@" index)
       "D=A"
       (str "@" (segment-abbreviation segment))
       "D=D+M"]))                                           ; For all others: Address = RAM[segment] + index
  )
; NOTE that only the last line is different

(defn pop-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'pop segment index'"
  [s filename]
  (let [[op segment index] (clojure.string/split s #" ")
        start (dynamic-pop-assembler-start segment index filename)
        other ["@R13"                                       ;NOTE: R13 is used for temporarily storing address for where top of stack (so result of pop) shall be written into
               "M=D"
               "@SP"
               "AM=M-1"
               "D=M"
               "@R13"
               "A=M"
               "M=D"]
        all (into [] (concat start other))]
    all))

(defmethod bytecode-to-assembler "pop" [str filename] (append-comment-to-last (pop-to-assembler str filename) str))

;; PART 2: THIS WEEK'S PROJECT
;; TODO: Wir mussten static von letzter Woche noch anpassen (damit mehrere Dateien statics haben können!)

; Letzte Woche kam das heraus:
;@16
;D=M
;@3
;A=D+A
;D=M
;@SP
;AM=M+1
;A=A-1
;M=D //push static 3

; Jetzt wollen wir das hier:
;@XXX.3 //Neue Symbole werden vom Projekt 6 einfach neu in Static vergeben!
;D=M
;@SP
;A=M+1
;A=A-1
;M=D

;; PART 2.1: PROGRAM FLOW

(defn label-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'label xyz'"
  [s]
  (let [[_ x] (clojure.string/split s #" ")]
    [(str "(" x ")")]
    ))
(defmethod bytecode-to-assembler "label" [s filename] (append-comment-to-last (label-to-assembler s) s))


(defn goto-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'goto xyz'"
  [s]
  (let [[_ x] (clojure.string/split s #" ")]
    [(str "@" x)
     "0;JMP"]
    ))
(defmethod bytecode-to-assembler "goto" [str filename] (append-comment-to-last (goto-to-assembler str) str))


;(defn if-goto-to-assembler
;  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'if-goto xyz'"
;  [s]
;  (let [[_ x] (clojure.string/split s #" ")]
;    ["@SP"
;     "A=A-1"
;     (str "@" x)
;     "M;JEQ"]
;    ))

;; NOTE: Da war was völlig falsch
(defn if-goto-to-assembler
  [s]
  (let [[_ x] (str/split s #" ")]
    ["@SP"
     "AM=M-1"                ;; pop: SP--, A=SP
     "D=M"                   ;; D = stack-top
     (str "@" x)
     "D;JNE"                 ;; if D != 0 goto x
     ]))

(defmethod bytecode-to-assembler "if-goto" [str filename] (append-comment-to-last (if-goto-to-assembler str) str))

;; PART 2.2: FUNCTION CALLING

(defn function-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'function g nVars'"
  [s]
  (let [[_ g nVars] (clojure.string/split s #" ")
        nVars (Integer/parseInt nVars)]
    (into []
          (concat [(str "(" g ")")]                         ;Label nicht vergessen!
                  (reduce concat (repeat nVars ["D=0" "@SP" "AM=M+1" "A=A-1" "M=D"]))))))

(defmethod bytecode-to-assembler "function" [str filename] (append-comment-to-last (function-to-assembler str) str))

;; Erklärung: Das ist eine Liste von Vektoren
;(repeat 5 ["D=0" "@SP" "AM=M+1" "A=A-1" "M=D"])
;; Das ist eine einzige Liste
;(reduce concat (repeat 5 ["D=0" "@SP" "AM=M+1" "A=A-1" "M=D"]))
;; Das ein Vektor
;(vec (reduce concat (repeat 5 ["D=0" "@SP" "AM=M+1" "A=A-1" "M=D"])))
; und damit das nicht lazy ist (besser für Debugging):
; into nutzen

(defn call-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'call g nArgs'"
  [string]
  (let [[_ g nArgs] (clojure.string/split string #" ")
        returnSymbol (java.util.UUID/randomUUID)
        pushReturn [(str "@" returnSymbol) "D=A" "@SP" "AM=M+1" "A=A-1" "M=D //push returnAddress (starting call g nargs)"]
        pushLCL ["@LCL" "D=M" "@SP" "AM=M+1" "A=A-1" "M=D //push LCL"]
        pushARG ["@ARG" "D=M" "@SP" "AM=M+1" "A=A-1" "M=D //push ARG"]
        pushTHIS ["@THIS" "D=M" "@SP" "AM=M+1" "A=A-1" "M=D //push THIS"]
        pushTHAT ["@THAT" "D=M" "@SP" "AM=M+1" "A=A-1" "M=D //push THAT"]
        substractor (+ (Integer/parseInt nArgs) 5)
        reposARG ["@SP" "D=M" (str "@" substractor) "D=D-A" "@ARG" "M=D //ARG = SP-nArgs-5"]
        reposLCL ["@SP" "D=M" "@LCL" "M=D //LCL=SP"]
        gotoG [(str "@" g ) "0;JMP //goto g (ending call g nargs)"]
        endingLabel [(str "(" returnSymbol ")")]]
    (into [] (concat pushReturn pushLCL pushARG pushTHIS pushTHAT reposARG reposLCL gotoG endingLabel))))

(defmethod bytecode-to-assembler "call" [str filename] (append-comment-to-last (call-to-assembler str) str))

;; NOTE:
;;  Convention 1: Value of temp. variable "frame" is stored at address R13
;;  Convention 2: Value of temp. variable "retrAddr" is stored at address R14
(defn return-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'return'"
  [string]
  (let [frameSave ["@LCL" "D=M" "@R13" "M=D //frame=LCL"]
        retrAddr ["@5" "A=D-A" "D=M" "@R14" "M=D //retrAddr = *(frame-5)"]                ;Value of R13 is still in D-register
        updateARG ["@SP" "AM=M-1" "D=M" "@ARG" "A=M" "M=D //*ARG = pop"]
        restoreSP ["@ARG" "D=M+1" "@SP" "M=D //SP=ARG+1"]
        restoreTHAT ["@R13" "AM=M-1" "D=M" "@THAT" "M=D //THAT=*(frame-1)"]
        restoreTHIS ["@R13" "AM=M-1" "D=M" "@THIS" "M=D //THIS=*(frame-2)"]
        restoreARG ["@R13" "AM=M-1" "D=M" "@ARG" "M=D //ARG=*(frame-3)"]
        restoreLCL ["@R13" "AM=M-1" "D=M" "@LCL" "M=D //LCL=*(frame-4)"]
        gotoRetrAddr ["@R14" "A=M" "0;JMP //goto retrAddr"]]
    (into [] (concat frameSave retrAddr updateARG restoreSP restoreTHAT restoreTHIS restoreARG restoreLCL gotoRetrAddr))
    ))

(defmethod bytecode-to-assembler "return" [str filename] (append-comment-to-last (return-to-assembler str) str))

;; PART 4: BOOTSTRAPPING CODE

(defn bootstrap
  "Returns vector of strings/rows of corresponding assembler code for bootstrap"
  []
  (into [] (concat
             ["@256" "D=A" "@SP" "M=D //SP=256"]
             (call-to-assembler "call Sys.init 0"))))

  ; PART 3: PUTTING EVERYTHING TOGETHER

(defn parse-bytecode-to-assembler [vmcode filename]
  (into []
        (mapcat #(bytecode-to-assembler % filename)
                vmcode)))

(defn parse [& args]
  (let [filepath (first args)
        output-path (clojure.string/replace filepath #"\.vm$" ".asm")
        filename (second (re-matches #".*/([^/]+)\.asm$" output-path))
        vmcode (read-file filepath)
        assembler-code (parse-bytecode-to-assembler vmcode filename)]
    (println "Filename is: " filename)
    (spit output-path (clojure.string/join "\n" assembler-code))
    (println "Successfully wrote into file" output-path)))

(defn parse-multiple
  "Liest beliebig viele .vm-Dateien ein, übersetzt sie in Assembler und schreibt
   alle Instruktionen in eine einzige .asm-Datei, benannt nach dem Namen des
   Ordners, in dem die erste .vm-Datei liegt."
  [& filepaths]
  (let [first-vm      (first filepaths)
        parent-dir    (.getParent (io/file first-vm))
        folder-name   (last (str/split parent-dir #"/"))
        output-path   (str parent-dir "/" folder-name ".asm")
        bootstrap-code (bootstrap)
        asm-lines     (into []
                            (concat
                              bootstrap-code
                              (mapcat (fn [vm-path]
                                        (let [filename (second (re-matches #".*/([^/]+)\.vm$" vm-path))
                                              vm-code  (read-file vm-path)]
                                          (parse-bytecode-to-assembler vm-code filename)))
                                      filepaths)))]
    (println "Erzeuge ASM-Datei:" output-path)
    (spit output-path (str/join "\n" asm-lines))
    (println "Fertig! Es wurden" (count asm-lines) "Zeilen Assembler in" output-path "geschrieben")))


(defn -main [& args]
  (if (empty? args)
    (println "No file or folder given to convert to .asm!")
    (let [file (io/file (first args))]
      (cond
        (.isFile file) (parse (.getPath file))
        (.isDirectory file)
        (let [vm-files (->> (.listFiles file)
                            (filter #(and (.isFile %)
                                          (.endsWith (.getName %) ".vm")))
                            (map #(.getPath %))
                            (into []))]
          (if (= 1 (count vm-files))
            (parse (first vm-files))
            (apply parse-multiple vm-files)))
        :else
        (println "Invalid path given.")))))

; Um direkt alle Tests zu Assembler umzuwandeln
;(defn tescht [& args]
;  (do
;    (parse "src/7/StackArithmetic/SimpleAdd/SimpleAdd.vm")
;    (parse "src/7/StackArithmetic/StackTest/StackTest.vm")
;    (parse "src/7/MemoryAccess/BasicTest/BasicTest.vm")
;    (parse "src/7/MemoryAccess/PointerTest/PointerTest.vm")
;    (parse "src/7/MemoryAccess/StaticTest/StaticTest.vm")))


; Tests ohne Bootstrap
;(defn tescht-2 [& args]
;  (do
;    ; Die drei ohne Function Calls nach AB
;    (parse "src/8/ProgramFlow/BasicLoop/BasicLoop.vm")              ;klappt
;    (parse "src/8/ProgramFlow/FibonacciSeries/FibonacciSeries.vm")  ;klappt
;    (parse "src/8/FunctionCalls/SimpleFunction/SimpleFunction.vm")  ;klappt
;    ; Diese auch ohne
;    (parse "src/8/FunctionCalls/NestedCall/NestedCall.vm")          ;klappt
;    ))
;
;(tescht-2)

; Tests mit Bootstrap
;(defn tescht-3 [& args]
;  (do
;    (parse-multiple
;      "src/8/FunctionCalls/FibonacciElement/Main.vm"
;      "src/8/FunctionCalls/FibonacciElement/NestedCall.vm")        ;klappt
;    (parse-multiple
;      "src/8/FunctionCalls/StaticsTest/Class1.vm"
;      "src/8/FunctionCalls/StaticsTest/Class2.vm"
;      "src/8/FunctionCalls/StaticsTest/NestedCall.vm")             ;klappt
;    ))
;
;(tescht-3)