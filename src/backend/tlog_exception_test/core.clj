(ns tlog-exception-test.core
  "The main application namespace"
  (:require [mount.core :as mount]
            [clojure.tools.logging :as log]
            [clojure.tools.cli :as cli]
            [clojure.string :as string]
            [robert.hooke :as hooke]
            [tlog-exception-test.system :as system]
            [tlog-exception-test.logging :as logging])
  (:gen-class))

(defn main-stop
  "Hook for -main side-effects on stop.

  For instance this function should call shutdown-agents, which is not
  desirable when stopping the app at the repl."
  [f & args]
  (apply f args)
  (shutdown-agents))

(defn main-start
  "Hook for -main side-effects on start.

  For instance this function should set .addShutdownHook and perform
  all the side effects that need to be avoided when working at the
  repl."
  [f & args]
  (.addShutdownHook (Runtime/getRuntime) (Thread. #(system/stop)))
  (apply f args))

(defn -main
  [& args]

  (hooke/add-hook #'tlog-exception-test.system/stop #'tlog-exception-test.core/main-stop)
  (hooke/add-hook #'tlog-exception-test.system/start #'tlog-exception-test.core/main-start)
  (system/start []))
