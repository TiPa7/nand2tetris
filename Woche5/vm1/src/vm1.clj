(ns vm1
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; READING THE FILE
(defn read-file
  "Reads file, removes comments, removes whitespaces"
  [filepath]
  (with-open [rdr (clojure.java.io/reader filepath)]
    (filter
      (fn [x] (not (empty? x)))
      (map
        #(clojure.string/replace %1 #"(//.*)" "")
        (reduce
          conj
          []
          (line-seq rdr))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; PART 1: ARITHMETIC AND LOGICAL STACK COMMANDS

(def add-assembler ["@SP", "AM=M-1", "D=M", "A=A-1", "M=M+D"])

(def sub-assembler ["@SP", "AM=M-1", "D=M", "A=A-1", "M=M-D"])

(def neg-assembler ["@SP", "A=M-1", "M=-M"])

; TODO: FÜR LABELS UUIDS EINFÜHREN! (bzw. Subroutinen)
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


(defmulti bytecode-to-assembler (fn [str]
                                  (first (clojure.string/split str #" "))))

(defmethod bytecode-to-assembler "add" [str] (append-comment-to-last add-assembler str))
(defmethod bytecode-to-assembler "sub" [str] (append-comment-to-last sub-assembler str))
(defmethod bytecode-to-assembler "neg" [str] (append-comment-to-last neg-assembler str))
(defmethod bytecode-to-assembler "eq" [str] (append-comment-to-last (eq-assembler) str))
(defmethod bytecode-to-assembler "gt" [str] (append-comment-to-last (gt-assembler) str))
(defmethod bytecode-to-assembler "lt" [str] (append-comment-to-last (lt-assembler) str))
(defmethod bytecode-to-assembler "and" [str] (append-comment-to-last and-assembler str))
(defmethod bytecode-to-assembler "or" [str] (append-comment-to-last or-assembler str))
(defmethod bytecode-to-assembler "not" [str] (append-comment-to-last not-assembler str))




;; PART 2: MEMORY ACCESS COMMANDS (push & pop)

(def segment-abbreviation {"argument" "ARG"
                           "local" "LCL"
                           "static" "16"    ; we start with new symbols at memory address 16
                           "this" "THIS"    ; "constant" ommited, see below
                           "that" "THAT"
                           "pointer" "3"    ; push pointer 0 ist für Adresse 3 (also THIS), push pointer 1 für 4 (also THAT)
                           "temp" "5"}      ; memory segment temp is [5,..,12]
  )

(defn dynamic-push-assembler-start
  "Returns first part (vector) of assembler code for VM push operation"
  [segment index]
  (if (= segment "constant")
    [(str "@" index), "D=A"]                                ;; For constant: RAM[SP] = index, SP++
    (if (or (= segment "pointer") (= segment "temp"))
      [(str "@" (segment-abbreviation segment))             ;; For pointer and temp: RAM[SP] = RAM[segment + index], SP++
       "D=A"
       (str "@" index)
       "A=D+A"
       "D=M"]
      [(str "@" (segment-abbreviation segment))             ; For all other segments: RAM[SP] = RAM[RAM[segment] + index], SP++
       "D=M"                                                ; ACHTUNG: NICHT D=A!!!
       (str "@" index)
       "A=D+A"
       "D=M"])
    )
  )


(defn push-to-assembler
          "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'push segment index'"
          [str]
          (let [[op segment index] (clojure.string/split str #" ")
                start (dynamic-push-assembler-start segment index)
                other ["@SP" "AM=M+1" "A=A-1" "M=D"]
                all (vec (concat start other))]
            all
            ))

(defmethod bytecode-to-assembler "push" [str] (append-comment-to-last (push-to-assembler str) str))

(defn dynamic-pop-assembler-start
  "Returns first part (vector) of assembler code for VM pop operation"
  [segment index]
  (if (or (= segment "pointer") (= segment "temp"))
    [(str "@" index)
     "D=A"
     (str "@" (segment-abbreviation segment))
     "D=D+A"]                                               ; For pointer and temp: Address = segment + index
    [(str "@" index)
     "D=A"
     (str "@" (segment-abbreviation segment))
     "D=D+M"])                                              ; For all others: Address = RAM[segment] + index
  )
; NOTE that only the last line is different

(defn pop-to-assembler
  "Returns vector of strings/rows of corresponding assembler code for given bytecode operation 'pop segment index'"
  [str]
  (let [[op segment index] (clojure.string/split str #" ")
        start (dynamic-pop-assembler-start segment index)
        other ["@R13"  ;NOTE: R13 is used for temporarily storing address for where top of stack shall be written into
               "M=D"
               "@SP"
               "AM=M-1"
               "D=M"
               "@R13"
               "A=M"
               "M=D"]
        all (vec (concat start other))]
    all))

(defmethod bytecode-to-assembler "pop" [str] (append-comment-to-last  (pop-to-assembler str) str))

;; PART 4 (optional): REMOVING REDUNDANT SUBROUTINES

(defn remove-redundant-subroutines [joined-code subroutine]
  (let [block subroutine
        block-str (clojure.string/join "\n" block)
        pattern (re-pattern (java.util.regex.Pattern/quote block-str))
        replaced (clojure.string/replace joined-code pattern "")
        was-present? (not= replaced joined-code)]
    (if was-present?
      (str replaced "\n" block-str)
      joined-code)))


; PART 3: PUTTING EVERYTHING TOGETHER


(defn parse-bytecode-to-assembler
  "Returns vector of assembler code. Is given vector of VM bytecode"
  [vmcode]
  (vec (mapcat bytecode-to-assembler vmcode)))


(defn parse [& args]
  (let [filepath (first args)
        output-path (clojure.string/replace filepath #"\.vm$" ".asm")
        vmcode (read-file filepath)
        assembler-code (parse-bytecode-to-assembler vmcode)]
    (spit output-path (clojure.string/join "\n" assembler-code))
    (println "Successfully wrote into file" output-path)
    ))


;(parse "src/7/StackArithmetic/SimpleAdd/SimpleAdd.vm")     ; Klappt

;(parse "src/7/StackArithmetic/StackTest/StackTest.vm")     ; Klappt

;(parse "src/7/MemoryAccess/BasicTest/BasicTest.vm")        ; Klappt, Fehler war bei push local 0 statt D=M ein D=A

;(parse "src/7/MemoryAccess/PointerTest/PointerTest.vm")    ; Klappt direkt :D

;(parse "src/7/MemoryAccess/StaticTest/StaticTest.vm")      ; Klappt auch direkt :D

(defn -main [& args]
  (parse (first args)))


; Um direkt alle Tests zu Assembler umzuwandeln
(defn tescht [& args]
  (do
    (parse "src/7/StackArithmetic/SimpleAdd/SimpleAdd.vm")
    (parse "src/7/StackArithmetic/StackTest/StackTest.vm")
    (parse "src/7/MemoryAccess/BasicTest/BasicTest.vm")
    (parse "src/7/MemoryAccess/PointerTest/PointerTest.vm")
    (parse "src/7/MemoryAccess/StaticTest/StaticTest.vm")))

(tescht)