(ns compile-time-resolve.test
  (:require [compile-time-resolve.core :refer [compile-time-resolve-and-invoke]]
            [compile-time-resolve.test2 :as test2 :refer [cake-3 cake-4] :rename {cake-4 cake-5}]))

(def x
  [(compile-time-resolve-and-invoke compile-time-resolve.test2/cake-1)
   (compile-time-resolve-and-invoke test2/cake-2)
   (compile-time-resolve-and-invoke cake-3)
   (compile-time-resolve-and-invoke cake-5)
   (compile-time-resolve-and-invoke +)])
