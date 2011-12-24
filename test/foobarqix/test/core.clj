(ns foobarqix.test.core
  (:use [foobarqix.core])
  (:use [clojure.test]))

(deftest test-to-ints-returns-a-sequence-of-int
	(are [x y] (= x (to-ints y))
 		[]				nil
 		[1]				1
 		[1 2 3] 	123))
	
	

