; Clojure REPL - Pcy

(ns pcy)

; Colour support
(defn paint
  [text code]
  (str \u001b "[" code "m" text \u001b "[39m"))
(defn paint-reset [] (str \u001b "[0m"))

(defn red     [text] (paint text 31))
(defn green   [text] (paint text 32))
(defn yellow  [text] (paint text 33))
(defn blue    [text] (paint text 34))
(defn magenta [text] (paint text 35))
(defn cyan    [text] (paint text 36))
(defn grey    [text] (paint text 30))

; By Viebel - http://stackoverflow.com/a/9569328/335412
(defn replace-several [content & replacements]
      (let [replacement-list (partition 2 replacements)]
        (reduce #(apply clojure.string/replace %1 %2) content replacement-list)))

; REPL
(defn ask
  "Readline and eval the user input"
  [n]
  (print (str "[" n "] " *ns* "> "))
  (flush)
  (let [input (read-line)]
    (if-not (= input "exit") 
      (println 
        (replace-several (str (load-string input)) 
          #"\[" (red "[")
          #"((?:(?<![0-9]))[0-9]{1,}(?:(?![m0-9])))" (cyan "$1") ; digits
          #"\(" (red "(")
          #"\)" (red ")")
          #"\]" (red "]")
        )
        (paint-reset))
    )
    (not= input "exit")))

(defn repl []
  (loop [n 1]
    (when (ask n) (recur (inc n)))
  ))

(println "Type exit to close this REPL session")
(repl)
