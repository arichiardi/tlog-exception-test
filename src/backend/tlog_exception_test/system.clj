(ns tlog-exception-test.system
  "The system, aka the core of your app"
  (:require [clojure.tools.logging :as log]
            [mount.core :as mount]
            [cprop.core :as c]
            [clojure.java.io :as io]
            [clojure.string :as str]
            [tlog-exception-test.env :as env]
            [tlog-exception-test.logging :as logging])
  (:import java.util.Properties))

(Thread/setDefaultUncaughtExceptionHandler
 (reify Thread$UncaughtExceptionHandler
   (uncaughtException [_ thread ex]
     (log/error ex "Uncaught exception on" (.getName thread)))))

(defn version!
  "Return the current version from the version.properties file."
  []
  (let [props (Properties.)]
    (try
      ;; side effect
      (some->> (or (io/resource "version.properties")
                   (io/file "version.properties"))
               io/reader
               (.load props))
      (catch java.io.FileNotFoundException ex
        (println "Handled exception -" (.getMessage ex))))
    (.getProperty props "VERSION")))

(defn greeting
  "Return the greeting (as string)"
  [config]
  (str (:greeting config) (when (:version config) (str " - " (:version config)))))

(defn make-config
  "Creates a default configuration map"
  []
  (merge env/defaults
         (c/load-config :merge [
                                {:version (if-let [version (version!)] version "0.0.0")}])))

(mount/defstate
  config
  :start (make-config))

(defn stop
  "Stop the system."
  []
  (mount/stop)
  (logging/without-logging-status)
  (log/info "<=< Stopped"))

(defn start
  "Start the system.

  Args are in the form returned by clojure.tools.cli/parse-opts."
  [args]
  (logging/with-logging-status)
  (mount/start-with-args args)
  (log/info ">=> Started" (greeting config))
  (let [ex (ex-info "Oh noes!" {:file "/tmp/boot.user8522818496965447008.clj", :line 71} )]
    (log/error ex "This is an error")))
