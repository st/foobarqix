(ns foobarqix.core)
	
;; ## Foobarqix in Clojure

;; _[http://www.code-story.net/](http://www.code-story.net/) presents a little challenge called foobarqix. It's the first phase to select a pair of programmers that will be part of the team led by David and Jean-Laurent at devoxx (april 2012)._
;; The following code is not a submission.
;; Instead a clojure solution (from an apprentice) written with simplicity in mind. I hope it may teach a little to newbies.
;; Feedbacks very welcome.

;; # Rules
;; They are declared only once here. The data structure used is a map.
;; This map simply associates the 'key' 7 to the value 'Qix', 5 to 'Bar' etc...
(def rules 	
	{7 "Qix" 5 "Bar" 3 "Foo"})

;; # to-ints
;; Convenience function returning the sequence of digits.
;; Let's explain this function from inside to outside.We build a string with x (we are expecting x to be a number) thanks to (str x). We then take the sequence of characters with seq. And we substract 48 to the int value for each element.
;; (The magic number 48 corresponds to the code of '0'.)

(defn to-ints 
	[x]
    (map #(- (int %) 48) (seq (str x))))

;; # has
;; This function applies the rule about presence of a digit.
;; First thing to note, the parameter is *destructured* in a vector with 2 parts : `x` and `accu`.
;; That simply means that the call with [123 "Nothing"] will bind x with 123 and accu with "Nothing".

;; We take advantage of the fact that a map `m` is also a function where (= value (m key)) and (nil? (m not-a-key)) for each key, value of `m`.
;; For example, (rules 2) returns nil, as (rules 5) returns Bar.
;; `to-ints` retrieves the list of digits `x`. For each digit we apply the `rules` map (remember it's also a function) thanks to `mapcat`. 
;; From `(doc mapcat)` : Returns the result of applying concat to the result of applying map to f and colls.  Thus function f should return a collection.

(defn has [[x accu]]
    [x (apply str accu (mapcat rules (to-ints x)))])

;; In case (very likely) where previous documentation was unclear, have a look at unit tests !

;; # div
;; This function applies the rules about the divisibility.
;; The functions corresponding to each number (3, 5 and 7) are actually produced by `div`.
;; A function that returns another function ? Welcome to the Clojure world. When a function outputs not a value but another function, we talk about a `higher-order` function.
;; The function returned is itself straightforward as it appends to `accu` only if the predicate (= 0 (rem x n)) is satisfied.
(defn div 
	[[n s]]
    (fn [[x accu]]
        [x (str accu (when (= 0 (rem x n)) s))]))        
        
;; # all
;; The functions `has` and `div` ones (one div function per rule entry) are just assembled in `all`. 
;; So `all` will compute the foobarqix rules for a given number.
(def all (cons has (map div (seq rules))))

;; # fbq
;; The function `all` is applied to the pair [n nil] for a given `n`. nil is the initial value taken for `accu`.
;; If accu contains something ((seq accu) is true) we return it else n is returned.
(defn fbq [n]
    (let [[n accu] ((apply comp all) [n nil])]
            (if (seq accu) accu n)))

;; # print-fbq-1-100 
;; We loop through the numbers of the range 1 to 100, and for each the `fbq` is computed.
(defn print-fbq-1-100 
	[]
	(doseq [i (range 1 101)]
    (println (fbq i))))
