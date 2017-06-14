(ns compile-time-resolve.test
  (:require [compile-time-resolve.core :refer [compile-time-resolve-and-invoke]]))

(def x (compile-time-resolve-and-invoke compile-time-resolve.test2/cake))
