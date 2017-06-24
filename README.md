This is a simple project demonstrating a proof of concept, compile-time, portable `resolve` function.

# About this project

`compile-time-resolve.core-macros` defines a `compile-time` macro, which emits its body only if it's compiled as clojure or self-hosted clojurescript (similar to `deftime` in [macrovich](https://github.com/cgrand/macrovich))

`compile-time-resolve.core` defines 3 functions: `eval` and `resolve` do what their name would suggest, and `compile-time-resolve-and-invoke` simply invokes `resolve` during its macroexpansion (it's there only to demonstrate `resolve`).

`compile-time-resolve.test2` defines a series of `cake-x` functions which return `cake-x`.

`compile-time-resolve.test` defines a var `x` with an initial value computed at compile time with the `cake-x` functions above.

# Try it out

## Clojure

With any clojure jar in the root folder:

```
> java -cp clj.jar:src clojure.main
```

Or, on windows:

```
> java -cp "clj.jar;src" clojure.main
```

Then, in the repl:

```
user=> (require '[compile-time-resolve.test :refer [x]])
nil
user=> x
:cake
```

Works!

## JVM ClojureScript

With the quick-start uberjar in the root folder:

```
> java -cp cljs.jar:src clojure.main build.clj
```

Or, on windows:

```
> java -cp "cljs.jar;src" clojure.main build.clj
```

Should be pretty quick, no error messages are expected. Check out `out/compile_time_resolve/test.js`, should contain something like:

```
// Compiled by ClojureScript 1.9.562 {}
goog.provide('compile_time_resolve.test');
goog.require('cljs.core');
goog.require('compile_time_resolve.core');
compile_time_resolve.test.x = new cljs.core.Keyword(null,"cake","cake",633028020);

//# sourceMappingURL=test.js.map
```

Absolutely no trace of our shenanigans!

## Self-hosted ClojureScript

I'll use [lumo](https://github.com/anmonteiro/lumo), but it should work equally well with any other self-hosted environment.

```
> lumo -c src
cljs.user=> (require '[compile-time-resolve.test :refer [x]])
nil
cljs.user=> x
:cake
```

No error messages, so we're good!
