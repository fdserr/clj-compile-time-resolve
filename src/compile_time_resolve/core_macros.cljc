(ns compile-time-resolve.core-macros
  #?(:cljs (:require-macros compile-time-resolve.core-macros)))

(defmacro compile-time
  "Emits the body at compile time, nothing at runtime."
  [& body]
  (when #?(:clj  (not (:ns &env))
           :cljs true)
    `(do ~@body)))
