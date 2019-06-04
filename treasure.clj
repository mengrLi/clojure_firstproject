(def len)
(def l)
(def m [])
(def r [])
(def flag 0)

(defn ^boolean is_valid [x y]
  (if (and (< x len) (< y l) (= (get-in r [x y]) "-") (>= x 0) (>= y 0))
    true
    false)
  )

(defn print_result [r]
  (println "Woo hoo, I found the treasure :-)")
  (def flag 1)
  (loop [x 0]
    (when (< x len)
      (println (clojure.string/join (get r x)))
      (recur (+ x 1))
      )
    )
  )

(defn print_f [r]
  (println "Uh oh, I could not find the treasure :-(")
  (loop [x 0]
    (when (< x len)
      (println (clojure.string/join (get r x)))
      (recur (+ x 1))
      )
    )
  )

(defn ^boolean solve_recur [x y]
  (if (is_valid x y)
    (do (def r (assoc-in r [x y] "+"))
        (if (solve_recur (+ x 1) y)
          true
          )
        (if (solve_recur x (+ y 1))
          true
          )
        (if (solve_recur (- x 1) y)
          true
          )
        (if (solve_recur x (- y 1))
          true
          )
        (def r (assoc-in r [x y] "!"))
        )
    (do (if (and (= (get-in r [x y]) "@") (not= flag 2))
          (print_result r)
          )
        )
    )
  )

(defn solve [content]
  (def a (get-in content [0 0]))
  (if (= a "-")
    (solve_recur 0 0)
    )
  )

(defn in_valid []
  (loop [x 1]
    (when (< x len)
      (if (not= (count (get m 0)) (count (get m x)))
        (def flag 2))
      (recur (+ x 1))
      )
    )
  )

(defn readFile []
  (def m [])
  (def r [])
  (println "This is my challenge:")
  (with-open [rdr (clojure.java.io/reader "map.txt")]
    (loop [line (.readLine rdr)]
      (when line
        (println line)
        (def m (conj m (clojure.string/split line #"")))
        (def r (conj r (clojure.string/split line #"")))
        (recur (.readLine rdr))))
    )
  (def len (count m))
  (def l (count (get m 0)))
  (in_valid )
  m
  )
(defn main []
  (def matrix (readFile))
  (solve matrix)
  (if (and (= flag 0) (not= flag 2))
    (print_f r))
  (if (= flag 2)
    (println "Map is not valid!"))
  )
(main)
