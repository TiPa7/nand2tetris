# Anleitung zum Ausführen des "Assemblers"


## A. Mit Kommandozeile 

### 1. Clojure installieren 
MacOS: ```brew install clojure/tools/clojure``` <br>
Windows weiß ich nicht :/

### 2. Navigiere in den Ordner assembler selber

### 3. Führe aus: 
```clojure -M -m assembler``` 

### 4. Die .hack-Dateien liegen jeweils auf Ebene der .asm-Dateien


## B. Mit IntelliJ (oder sonst einer IDE)

### 1. Cursive-Plugin installieren 
### 2. REPL aufsetzen unter den run-Configurations als "nRepl" 
### 3. assembler.clj öffnen 
### 4. Rechtklick und navigiere zu REPL (bei mir weit unten) -> Load file in REPL (Unter MacOS: Shift+CMD+L)
Das führt die gesamte .clj aus, damit auch die main-Methode. Die .hack-Dateien werden entsprechend erstellt.


## An der Main sollte ein grüner Pfeil sein. Den anzuklicken **sollte** auch reichen.