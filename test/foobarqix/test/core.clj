(ns foobarqix.test.core
  (:use [foobarqix.core])
  (:use [clojure.test])
	(:use [clojure.string :as str :only (split-lines)]))

(deftest test-to-ints-returns-a-sequence-of-int
	(are [x y] (= y (to-ints x))
 		nil				[]
 		1					[1]
 		123				[1 2 3]))
	
(deftest test-has-appends-char-if-number-contains-digit
	(are [x y] (= y (has x))
		[12 "Anything"]		[12		"Anything"]		 
		[123 "Anything"] 	[123 	"AnythingFoo"] 
 		[7553 ""]					[7553 "QixBarBarFoo"]))	       	

(deftest test-div-produces-a-div-predicate-function
	(let [div-3-Foo (div [3 "Foo"])]
		(are [x y] (= y (div-3-Foo x))
			[2 ""]	[2 ""]
			[15 ""]	[15 "Foo"])))

;; Copy-pasted from Laurent Petit solution
;; https://gist.github.com/1507832
(def first-hundred-results
"1
2
FooFoo
4
BarBar
Foo
QixQix
8
Foo
Bar
11
Foo
Foo
Qix
FooBarBar
16
Qix
Foo
19
Bar
FooQix
22
Foo
Foo
BarBar
26
FooQix
Qix
29
FooBarFoo
Foo
Foo
FooFooFoo
Foo
BarQixFooBar
FooFoo
FooQix
Foo
FooFoo
Bar
41
FooQix
Foo
44
FooBarBar
46
Qix
Foo
Qix
BarBar
FooBar
Bar
BarFoo
FooBar
BarBarBar
QixBar
FooBarQix
Bar
Bar
FooBar
61
62
FooQixFoo
64
BarBar
Foo
Qix
68
Foo
BarQixQix
Qix
FooQix
QixFoo
Qix
FooBarQixBar
Qix
QixQixQix
FooQix
Qix
Bar
Foo
82
Foo
FooQix
BarBar
86
FooQix
88
89
FooBar
Qix
92
FooFoo
94
BarBar
Foo
Qix
Qix
Foo
Bar")

(deftest test-first-hundred
	(dorun (map #(is (= %1 (str (fbq %2))))
		(str/split-lines first-hundred-results)
		(range 1 101))))