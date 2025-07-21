(ns assembler
  (:require [clojure.string :as str]
            [clojure.math.combinatorics :as combo]))



(def labels (atom {:counter 16 :map {} :out []}))
;; map shall contain mapping of labels
;; counter shall contain the corresponding address for a new undeclared label
;; out shall contain the final lines for ourput

(defmacro print-and-return-finished-instruction
  "Prints and returns the value"
  [x]
  `(do
     (println ~x)
     ~x))

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
        (map
          #(clojure.string/replace %1 #"\s*" "")
          (reduce
            conj
            []
            (line-seq rdr)))))))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; FIRST PASS: CREATING MAP FOR LABELS

;(defn process-labels!
;  "Saves the labels according line numbers in the atom labels"
;  [lines linecount]
;  (if (empty? lines)
;    (:map @labels)
;    (if (clojure.string/starts-with? (first lines) "(")
;      (dosync
;        (swap! labels
;          (fn [state]
;            (assoc state :map
;              (assoc (:map state)
;                (clojure.string/join (rest (butlast (first lines))))
;                linecount))))
;        (process-labels! (rest lines) (inc linecount)))
;      (process-labels! (rest lines) (inc linecount)))))

(defn process-labels
  "Saves the labels according line numbers in the atom labels"
  [state lines linecount]
  (if (empty? lines)
    state
    (if (clojure.string/starts-with? (first lines) "(")
      (let [new-state (assoc state :map
                                   (assoc (:map state)
                                     (clojure.string/join (rest (butlast (first lines))))
                                     (str linecount)))]
        (recur new-state (rest lines) linecount))
      (recur state (rest lines) (inc linecount)))))


(defn process-labels-start [state lines]
  (process-labels state lines 0))



;; Example:
;; Max.asm should have {"ITSR0" 10, "OUTPUT_D" 13, "END" 16}
;; The counter shall remain 16
(let [filee (read-file "src/6/max/Max.asm")]
  (process-labels-start @labels filee))

@labels
(:map @labels)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; SECOND PASS: TRANSLATING EACH INSTRUCTION TO BINARY


;; PART 1: TRANSLATING A-INSTRUCTIONS

(defn normalize-binary-string [s]
  (if (= 15 (count s))
    (reduce str s)
    (if (< 15 (count s))
      (normalize-binary-string (rest s))
      (normalize-binary-string (str "0" s)))))

(normalize-binary-string "1111111111111111")


;; NOTE: This method is called WITHOUT the leading @ of the A-Instructions
;(defn parse-a-instruction!
;  "If instruction (called WITHOUT leading @) is symbol: lookup the symbol/create new one. Else: Parse to binary with 15 digits"
;  [instruction]
;  (swap! labels
;         (fn [state]
;           (try
;             (assoc state :out
;                          (conj (:out state) (normalize-binary-string (Integer/toBinaryString (Integer/parseInt instruction)))))
;             (catch Exception e
;               (if (nil? (get (:map state) instruction))
;                 (let [digit (:counter state)
;                       new-map (assoc (:map state) instruction digit)
;                       new-state (assoc state :map new-map)
;                       inced-state (update new-state :counter inc)
;                       out-string (normalize-binary-string (Integer/toBinaryString digit))
;                       final-out (conj (:out inced-state) out-string)
;                       final (assoc inced-state :out final-out)]
;                   final)
;                 (let [label-digit (get instruction (:map state))
;                       out-string (normalize-binary-string (Integer/toBinaryString label-digit))
;                       final-out (conj (:out state) out-string)
;                       final (assoc state :out final-out)]
;                   final)))))))


(defn parse-a-instruction
  "If instruction (called WITHOUT leading @) is symbol: lookup the symbol/create new one. Else: Parse to binary with 15 digits"
  [state instruction]
  (try
    (let [parsed-number (Integer/parseInt instruction)]
      (if (> parsed-number 32767)
        (throw (IllegalArgumentException. "Invalid number: Larger than 32767 cannot be represented with 15 Bits")))
      (assoc state :out
                   (conj (:out state) (str "0" (normalize-binary-string (Integer/toBinaryString parsed-number))))))
    (catch NumberFormatException e
      (if (nil? (get (:map state) instruction))
        (let [digit (:counter state)
              new-map (assoc (:map state) instruction (str digit))
              new-state (assoc state :map new-map)
              inced-state (update new-state :counter inc)
              out-string (str "0" (normalize-binary-string (Integer/toBinaryString digit)))
              final-out (conj (:out inced-state) out-string)
              final (assoc inced-state :out final-out)]
          final)
        (let [label-digit (get (:map state) instruction)
              out-string (str "0" (normalize-binary-string (Integer/toBinaryString (Integer/parseInt label-digit))))
              final-out (conj (:out state) out-string)
              final (assoc state :out final-out)]
          final)))))

@labels
(parse-a-instruction @labels "1")

(parse-a-instruction @labels "sollteAuf16Landen")

(parse-a-instruction @labels "sollteNichtAuf17LandenWeilLabelsUnverändert")



;@labels

;; PART 2: TRANSLATING C-INSTRUCTIONS

;; PART 2.1: Translating the comp-bits
;; NOTE: comp is never empty
;;
(defn get-comp-encoding [comp-string]
  (let [encoding-map {"0" "0101010"
                      "1" "0111111"
                      "-1" "0111010"
                      "D" "0001100"
                      "A" "0110000"
                      "M" "1110000"
                      "!D" "0001101"
                      "!A" "0110001"
                      "!M" "1110001"
                      "-D" "0001111"
                      "-A" "0110011"
                      "-M" "1110011"
                      "D+1" "0011111"
                      "A+1" "0110111"
                      "M+1" "1110111"
                      "D-1" "0001110"
                      "A-1" "0110010"
                      "M-1" "1110010"
                      "D+A" "0000010"
                      "D+M" "1000010"
                      "D-A" "0010011"
                      "D-M" "1010011"
                      "A-D" "0000111"
                      "M-D" "1000111"
                      "D&A" "0000000"
                      "D&M" "1000000"
                      "D|A" "0010101"
                      "D|M" "1010101"}]
    (get encoding-map comp-string)))


;; PART 2.2: Translating the dest-bits
;; NOTE: dest can be empty

(defmulti get-dest-encoding (fn [dest-string] (count dest-string)))

(defmethod get-dest-encoding 0 [dest-string] "000")

(defmethod get-dest-encoding 1 [dest-string]
  (if (= dest-string "M")
    "001"
    (if (= dest-string "D")
      "010"
      (if (= dest-string "A")
        "100"
        (throw (IllegalArgumentException. "Invalid destination"))))))

(defmethod get-dest-encoding 2 [dest-string]
  (if (or (= dest-string "MD") (= dest-string "DM"))
    "011"
    (if (or (= dest-string "AM") (= dest-string "MA"))
      "101"
      (if (or (= dest-string "AD") (= dest-string "DA"))
        "110"
        (throw (IllegalArgumentException. "Invalid destination"))))))

(defmethod get-dest-encoding 3 [dest-string]
  (let [permutations (set (map #(apply str %) (combo/permutations "AMD")))]
    (if (contains? permutations dest-string)
      "111"
      (throw (IllegalArgumentException. "Invalid destination")))))



;; PART 2.3: Translating the jump-bits
;; NOTE: jump can be empty

(defn get-jump-encoding [jump-string]
  (let [encoding-map {nil "000"
                      "JGT" "001"
                      "JEQ" "010"
                      "JGE" "011"
                      "JLT" "100"
                      "JNE" "101"
                      "JLE" "110"
                      "JMP" "111"}]
    (get encoding-map jump-string)))


;; PART 2.4: Full c-instruction-parsing

;;NOTE: #"" marks a RegEx
;(defn parse-c-instruction [state instruction]
;  (let [front "111"
;        parts (str/split instruction #"=")
;        [dest comp+] (if  (= 1 (count parts))
;                       [nil (first parts)]
;                       parts)
;        [comp jump] (str/split comp+ #";")]
;    (do
;      (println (str "comp: " comp " -> " (get-comp-encoding comp)))
;      (println (str "dest: " dest " -> " (get-dest-encoding dest)))
;      (println (str "jump: " jump " -> " (get-jump-encoding jump))))
;    (str front (get-comp-encoding comp) (get-dest-encoding dest) (get-jump-encoding jump))))
;

(defn parse-c-instruction
  "Parses a C-instruction, adding the binary string to the output list in the state."
  [state instruction]
  (let [front "111"
        parts (str/split instruction #"=")
        [dest comp+] (if (= 1 (count parts))
                       [nil (first parts)]
                       parts)
        [comp jump] (str/split comp+ #";")]
    (try
      (println (str "comp: " comp " -> " (get-comp-encoding comp)))
      (println (str "dest: " dest " -> " (get-dest-encoding dest)))
      (println (str "jump: " jump " -> " (get-jump-encoding jump)))
      (let [new-state (assoc state :out
                                   (conj (:out state) (str front
                                                           (get-comp-encoding comp)
                                                           (get-dest-encoding dest)
                                                           (get-jump-encoding jump))))]
        new-state)
      (catch IllegalArgumentException e
        (throw (IllegalArgumentException. (str "Error for instruction " instruction)))
        ))))

(def labels (atom {:counter 16 :map {} :out []}))

;; Tests
(let [result-state (parse-c-instruction @labels "ADM=A+1;JMP")
      generated-binary (last (:out result-state))]
  (= "1110110111111111" generated-binary))

(let [result-state (parse-c-instruction @labels "0;JMP")
      generated-binary (last (:out result-state))]
  (= "1110101010000111" generated-binary))

(let [result-state (parse-c-instruction @labels "ADM=A+1")
      generated-binary (last (:out result-state))]
  (= "1110110111111000" generated-binary))




;; PART 3: Putting everything together
(def std-map {:counter 16
              :map {"SCREEN" "16384"
                    "KBD" "24576"
                    "SP" "0"
                    "LCL" "1"
                    "ARG" "2"
                    "THIS" "3"
                    "THAT" "4"
                    "R0" "0"
                    "R1" "1"
                    "R2" "2"
                    "R3" "3"
                    "R4" "4"
                    "R5" "5"
                    "R6" "6"
                    "R7" "7"
                    "R8" "8"
                    "R9" "9"
                    "R10" "10"
                    "R11" "11"
                    "R12" "12"
                    "R13" "13"
                    "R14" "14"
                    "R15" "15"}
              :out []})

(def labels (atom std-map))

(defn assemble
  [filepath]
  (let [file-content (read-file filepath)
        output-path (clojure.string/replace filepath #"\.asm$" ".hack")
        final-state (reduce (fn [current-state line]
                              (do
                                (println "Current line:" line)
                                (if (str/starts-with? line "@")
                                  (parse-a-instruction current-state (subs line 1))
                                  (if (str/starts-with? line "(")
                                    current-state
                                    (parse-c-instruction current-state line))
                                  )))
                            ;; Initialisiere State hier, nicht im Atom
                            (process-labels-start std-map file-content)
                            file-content)]
    ;; Einziger Zugriff auf das Atom: reset! des gesamten States
    (reset! labels final-state)
    ;; Schreibe die generierten Codes in die Datei
    (spit output-path (clojure.string/join "\n" (:out final-state)))
    output-path))



;(do
;  (assemble "src/6/add/Add.asm")
;
;  (assemble "src/6/max/Max.asm")
;
;  (assemble "src/6/max/MaxL.asm")
;
;  (assemble "src/6/pong/Pong.asm")
;
;  (assemble "src/6/pong/PongL.asm")
;
;  (assemble "src/6/rect/Rect.asm")
;
;  (assemble "src/6/rect/RectL.asm"))

(defn -main [& args]
  (do
    (assemble "src/6/add/Add.asm")
    (assemble "src/6/max/Max.asm")
    (assemble "src/6/max/MaxL.asm")
    (assemble "src/6/pong/Pong.asm")
    (assemble "src/6/pong/PongL.asm")
    (assemble "src/6/rect/Rect.asm")
    (assemble "src/6/rect/RectL.asm")))