(ns foobarqix.core)

;; ## Foobarqix in Clojure
;; Work In Progress... !
;; A simple solution (from a Clojure newbye) written with explicitness in mind. So that it may teach a little to someone without any Clojure knowledge.

;; # Rules
;; They are declared only once here. The data structure used is a map.
;; This map simply associates the 'key' 7 to the value 'Quix', etc...
(def rules 	
	{7 "Qix" 5 "Bar" 3 "Foo"})

;; # Higher order functions ?
(defn div 
	"A function that returns another function ? Welcome to the Clojure world. In that case, it means that this function 'div' will output not a value but another function."
	[[n s]]
    (fn [[x accu]]
        [x (str accu (when (= 0 (rem x n)) s))]))        
        
(defn to-ints 
		"Convenience function returning the sequence of digits. The magic number 48 corresponds to the code of '0'. Let's explain this function from inside to outside.We build a string with x thanks to (str x). Then we take the sequence of characters with seq. We then apply a function (substract 48 to the int value) for each element.
		"
	[x]
    (map #(- (int %) 48) (seq (str x))))

(defn has [[x accu]]
    [x (apply str accu (mapcat rules (to-ints x)))])

(def all (cons has (map div (seq rules))))

(defn fbq [n]
    (let [[n s] ((apply comp all) [n nil])]
            (if (seq s) s n)))

(defn print-fbq-1-100 
	[]
	(doseq [i (range 1 101)]
    (println (fbq i))))
