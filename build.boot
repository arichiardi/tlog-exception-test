;;;;;;;;;;;;;;;;;;;;;;
;;;  Dependencies  ;;;
;;;;;;;;;;;;;;;;;;;;;;

(def cmd-line-deps '[[degree9/boot-semver "1.3.5" :scope "test" :exclusions [org.clojure/clojure com.google.guava/guava]]
                     [adzerk/env "0.3.0" :scope "test"]
                     [pandeiro/boot-http "0.7.3" :scope "test" :exclusions [org.clojure/clojure]]])

(def backend-deps '[[org.clojure/clojure "1.8.0" :scope "provided"]
                    [org.clojure/tools.namespace "0.3.0-alpha3"]
                    [org.clojure/tools.reader "0.10.0"]
                    [org.clojure/tools.cli "0.3.3"]
                    [org.clojure/tools.logging "0.4.0"]
                    [org.apache.logging.log4j/log4j-api "2.5" :scope "runtime"]
                    [org.apache.logging.log4j/log4j-core "2.5" :scope "runtime"]
                    [org.apache.logging.log4j/log4j-jcl "2.5" :scope "runtime"]
                    [org.apache.logging.log4j/log4j-jul "2.5" :scope "runtime"]
                    [org.apache.logging.log4j/log4j-1.2-api "2.5" :scope "runtime"]
                    [org.apache.logging.log4j/log4j-slf4j-impl "2.5" :scope "runtime"]
                    [mount "0.1.10"]
                    [robert/hooke "1.3.0"]
                    [cprop "0.1.8"]
                    ;; dev only
                    [boot/core "2.6.0" :scope "test"]
                    [boot/pod "2.6.0" :scope "test"]
                    [adzerk/boot-test "1.1.1" :scope "test"]
                    [adzerk/env "0.3.0" :scope "test"]])

(def backend-exclusions '#{org.clojure/clojurescript})

(set-env! :source-paths #{"dev"}
          :dependencies cmd-line-deps)

(require '[boot :refer [run]]
         '[clojure.pprint :refer [pprint]]
         '[clojure.string :as string]
         '[boot.pod :as pod]
         '[boot.util :as util]
         '[boot-semver.core :refer :all]
         '[pandeiro.boot-http :refer [serve]]
         '[adzerk.env :as env])

(def +version+ (get-version))

(task-options! pom {:project 'tlog-exception-test
                    :version +version+
                    :url "http://example.com/FIXME"
                    :description "FIXME: write description"
                    :license {}}
               run {:main-ns 'tlog-exception-test.core})

;;;;;;;;;;;;;;;;;;;;;;;
;;;   Environment   ;;;
;;;;;;;;;;;;;;;;;;;;;;;

;; Note that these are treated as system properties, see https://github.com/adzerk-oss/env/issues/2
;; and adopt the syntax for cprop: https://github.com/tolitius/cprop#system-properties-cprop-syntax

(env/def
  BOOT_DEFAULT_FLAVOR "backend")

;;;;;;;;;;;;;;;;;;;;;;;
;;  BACKEND OPTIONS  ;;
;;;;;;;;;;;;;;;;;;;;;;;

(def backend-options
  {:env {:dependencies backend-deps
         :exclusions backend-exclusions
         :source-paths #{"src/backend" "src/common"}}
   :repl {:init-ns 'dev
          :port 5055}
   :jar {:main 'tlog-exception-test.core
         :file "tlog-exception-test-standalone.jar"}
   :aot {:all true}})

(defmethod boot/options [:backend :dev]
  [selection]
  (-> backend-options
      (update-in [:env :source-paths] conj "env/dev/src" "dev")
      (assoc-in [:env :resource-paths] #{"env/dev/resources"})
      (assoc-in [:env :middleware] @@(resolve 'boot.repl/*default-middleware*))))

(defmethod boot/options [:backend :prod]
  [selection]
  (-> backend-options
      (update-in [:env :source-paths] conj "env/prod/src")
      (assoc-in [:env :resource-paths] #{"env/prod/resources"})))

(defmethod boot/options [:backend :test]
  [selection]
  (-> backend-options
      (update-in [:env :source-paths] conj "test/backend" "env/dev/src")
      (assoc-in [:env :resource-paths] #{"env/test/resources"})
      (assoc-in [:test :namespaces] #{'tlog-exception-test.system-test
                                      'tlog-exception-test.utils-test})))

;;;;;;;;;;;;;;;;;;
;;  MAIN TASKS  ;;
;;;;;;;;;;;;;;;;;;

(deftask build
  "Build the final artifact.

  In order to allow task chaining (\"boot build deploy\" at the cmd line for
  instance), building all flavors at the same time is not supported at the
  moment. This means that the build task requires a --flavor and if missing it
  will read it from BOOT_DEFAULT_FLAVOR.

  Optionally you can specify a build type (dev or prod are supported out of
  the box). If no type is passed in, prod will be build.

  The option --o|--out-folder will keep the main.out folder in the fileset,
  which is otherwise removed."
  [f flavor VAL kw   "The flavor"
   t type   VAL kw   "The build type, either prod or dev"
   o out-folder bool "Include main.out folder."]
  (let [type (or type :prod)
        flavor (or flavor (keyword (get (env/env) "BOOT_DEFAULT_FLAVOR")))]
    (assert flavor "Cannot build without a flavor. Either specify it with -f/--flavor or set BOOT_DEFAULT_FLAVOR")
    (util/info "Will build the [%1s %2s] profile...\n" flavor type)
    (let [options (boot/options [flavor type])]
      (case flavor
        :backend (boot/build-backend options)))))

(deftask dev
  "Start the development interactive environment.

  If no flavor is specified, both sessions will be started. The defaults are:
  - port 5055 for the backend
  - port 5088 for the frontend (call adzerk.boot-cljs-repl/start-repl and then
  point your browser to http://localhost:3000)."
  [f flavor VAL kw   "The flavor (backend or frontend)"
   D dirac      bool "Enable the Dirac repl instead of the standard one."]
  (boot.util/info "Starting interactive dev...\n")
  (let [dev-backend #(boot/dev-backend (boot/options [:backend :dev]))]
    (case flavor
      :backend (comp (dev-backend) (wait))
      (comp (dev-backend)
            (wait)))))

(ns-unmap 'boot.user 'test)

(deftask test
  "Run tests once.

  If no flavor is specified, all the tests for all the flavors will be
  triggered.

  If no type is passed in, it tests against the production build.

  If no namespace option is specified, it tests all the namespaces in the
  classpath except the symbols in the exclusion set.

  In order to enable auto testing, prepend this task with watch, e.g. boot
  watch test."
  [f flavor     VAL        kw        "The flavor"
   n namespace  NS         #{sym}   "Override and test only this namespace"
   e exclusion  REGEX      #{sym}   "Exclude this namespace"]
  (let [flavor (or flavor (keyword (get (env/env) "BOOT_DEFAULT_FLAVOR")))]
    (assert flavor "Cannot build without a flavor. Either specify it with -f/--flavor or set BOOT_DEFAULT_FLAVOR")
    (letfn [(test-backend [] (-> (boot/options [:backend :test])
                                 (boot/boot-test-opts namespace exclusion)
                                 boot/test-backend))]
      (case flavor
        :backend (test-backend)))))

(deftask deps
  "Show the dependency tree

  If no -f|--flavor is specified it will be read from BOOT_DEFAULT_FLAVOR.
  If no -t|--type is specified the task will assume :prod."
  [f flavor VAL kw   "The flavor"
   t type   VAL kw   "The build type, either prod or dev"
   D dirac      bool "Enable the Dirac repl instead of the standard one."]
  (let [type (or type :prod)
        flavor (or flavor (keyword (get (env/env) "BOOT_DEFAULT_FLAVOR")))]
    (assert flavor "Cannot list dependencies without a flavor. Either specify it with -f/--flavor or set BOOT_DEFAULT_FLAVOR")
    (boot/deps (boot/options [flavor type]) dirac)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;  see dev/boot.clj for task customization  ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
