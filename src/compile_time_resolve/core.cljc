(ns compile-time-resolve.core
  (:refer-clojure :exclude [resolve])
  (:require [compile-time-resolve.core-macros :refer [compile-time]])
  #?(:cljs (:require-macros compile-time-resolve.core)))

(compile-time
  ;; Do these here, let's not mess with the JVM cljs ns form (maybe it messes up advanced builds?)
  #?(:cljs (require '[cljs.js :as cljs]
                    '[cljs.env :as env])))

(compile-time
  #?(:cljs
     (defn eval
       "Evaluates the expression."
       [expr]
       (let [result (volatile! nil)]
         (cljs/eval env/*compiler*
                    expr
                    {:ns      (.-name *ns*)
                     :context :expr}
                    (fn [{:keys [value error]}]
                      (if error
                        (throw (js/Error. (str error)))
                        (vreset! result value))))
         @result))))

(compile-time
  (defn resolve
    "Resolves a fully qualified symbol to a var. Does not require its namespace to be already required."
    [env sym]
    #?(:clj  (do (when (some? (:ns env))
                   (require (.getName ^clojure.lang.Namespace *ns*)))
                 (clojure.core/resolve sym))
       :cljs (eval `(~'var ~sym)))))

(compile-time
  (defmacro compile-time-resolve-and-invoke
    "Resolves the symbol at compile time and invokes the function bound to it, emitting the result."
    [sym]
    ((resolve &env sym))))
