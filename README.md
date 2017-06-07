# Tlog-exception-test

## Reproduce

Compare the output of `boot run`:

```
Retrieving tools.logging-0.4.0.pom from https://repo1.maven.org/maven2/ (2k)
Retrieving tools.logging-0.4.0.jar from https://repo1.maven.org/maven2/ (7k)
13:13:16.545 [clojure-agent-send-off-pool-0] INFO  tlog-exception-test.system - >=> Started Project Tlog-exception-test - 0.1.0-SNAPSHOT
13:13:16.546 [clojure-agent-send-off-pool-0] ERROR tlog-exception-test.system - This is an error
clojure.lang.ExceptionInfo: Oh noes!
	at clojure.core$ex_info.invokeStatic(core.clj:4617) ~[clojure-1.8.0.jar:?]
	at clojure.core$ex_info.invoke(core.clj:4617) ~[clojure-1.8.0.jar:?]
	at tlog_exception_test.system$start.invokeStatic(system.clj:62) [?:?]
	at tlog_exception_test.system$start.invoke(system.clj:54) [?:?]
	at clojure.lang.AFn.applyToHelper(AFn.java:154) [clojure-1.8.0.jar:?]
	at clojure.lang.AFn.applyTo(AFn.java:144) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invokeStatic(core.clj:646) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invoke(core.clj:641) [clojure-1.8.0.jar:?]
	at tlog_exception_test.core$main_start.invokeStatic(core.clj:29) [?:?]
	at tlog_exception_test.core$main_start.doInvoke(core.clj:21) [?:?]
	at clojure.lang.RestFn.invoke(RestFn.java:423) [clojure-1.8.0.jar:?]
	at clojure.lang.Var.invoke(Var.java:383) [clojure-1.8.0.jar:?]
	at clojure.lang.AFn.applyToHelper(AFn.java:156) [clojure-1.8.0.jar:?]
	at clojure.lang.Var.applyTo(Var.java:700) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invokeStatic(core.clj:648) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invoke(core.clj:641) [clojure-1.8.0.jar:?]
	at robert.hooke$compose_hooks$fn__2654.doInvoke(hooke.clj:40) [?:?]
	at clojure.lang.RestFn.applyTo(RestFn.java:137) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invokeStatic(core.clj:646) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invoke(core.clj:641) [clojure-1.8.0.jar:?]
	at robert.hooke$run_hooks.invokeStatic(hooke.clj:46) [?:?]
	at robert.hooke$run_hooks.invoke(hooke.clj:45) [?:?]
	at robert.hooke$prepare_for_hooks$fn__2659$fn__2660.doInvoke(hooke.clj:54) [?:?]
	at clojure.lang.RestFn.applyTo(RestFn.java:137) [clojure-1.8.0.jar:?]
	at clojure.lang.AFunction$1.doInvoke(AFunction.java:29) [clojure-1.8.0.jar:?]
	at clojure.lang.RestFn.invoke(RestFn.java:408) [clojure-1.8.0.jar:?]
	at tlog_exception_test.core$_main.invokeStatic(core.clj:36) [?:?]
	at tlog_exception_test.core$_main.doInvoke(core.clj:31) [?:?]
	at clojure.lang.RestFn.invoke(RestFn.java:397) [clojure-1.8.0.jar:?]
	at clojure.lang.Var.invoke(Var.java:375) [clojure-1.8.0.jar:?]
	at clojure.lang.AFn.applyToHelper(AFn.java:152) [clojure-1.8.0.jar:?]
	at clojure.lang.Var.applyTo(Var.java:700) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invokeStatic(core.clj:646) [clojure-1.8.0.jar:?]
	at clojure.core$apply.invoke(core.clj:641) [clojure-1.8.0.jar:?]
	at boot$eval423$fn__424$fn__431$fn__432.invoke(boot.clj:119) [?:?]
	at boot.core$run_tasks.invoke(core.clj:938) [core-2.6.0.jar:?]
	at boot.core$boot$fn__933.invoke(core.clj:948) [core-2.6.0.jar:?]
	at clojure.core$binding_conveyor_fn$fn__4676.invoke(core.clj:1938) [clojure-1.8.0.jar:?]
	at clojure.lang.AFn.call(AFn.java:18) [clojure-1.8.0.jar:?]
	at java.util.concurrent.FutureTask.run(FutureTask.java:266) [?:1.8.0_131]
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142) [?:1.8.0_131]
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617) [?:1.8.0_131]
	at java.lang.Thread.run(Thread.java:748) [?:1.8.0_131]
13:13:16.556 [Thread-14] INFO  tlog-exception-test.system - <=< Stopped
```

With the output of `boot dev` (note the `{:file "/tmp/boot.user8522818496965447008.clj", :line 71}`) :

```
Starting interactive dev...
nREPL server started on port 5055 on host 127.0.0.1 - nrepl://127.0.0.1:5055
13:14:21.164 [clojure-agent-send-off-pool-0] DEBUG tlog-exception-test.logging - >=> starting #'tlog-exception-test.system/config
13:14:21.177 [clojure-agent-send-off-pool-0] INFO  tlog-exception-test.system - >=> Started Project Tlog-exception-test (DEV) - 0.1.0-SNAPSHOT
13:14:21.177 [clojure-agent-send-off-pool-0] ERROR tlog-exception-test.system - This is an error
clojure.lang.ExceptionInfo: Oh noes! {:file "/tmp/boot.user8522818496965447008.clj", :line 71}
	at clojure.core$ex_info.invokeStatic(core.clj:4617)
	at clojure.core$ex_info.invoke(core.clj:4617)
	at tlog_exception_test.system$start.invokeStatic(system.clj:62)
	at tlog_exception_test.system$start.invoke(system.clj:54)
	at dev$start.invokeStatic(dev.clj:28)
	at dev$start.invoke(dev.clj:28)
	at dev$go$fn__3565.invoke(dev.clj:33)
	at dev$go.invokeStatic(dev.clj:33)
	at dev$go.invoke(dev.clj:32)
	at pod$eval3578.invokeStatic(NO_SOURCE_FILE:0)
	at pod$eval3578.invoke(NO_SOURCE_FILE)
	at clojure.lang.Compiler.eval(Compiler.java:6927)
	at clojure.lang.Compiler.eval(Compiler.java:6917)
	at clojure.lang.Compiler.eval(Compiler.java:6890)
	at clojure.core$eval.invokeStatic(core.clj:3105)
	at clojure.core$eval.invoke(core.clj:3101)
	at boot.pod$eval_in_STAR_.invoke(pod.clj:437)
	at clojure.lang.Var.invoke(Var.java:379)
	at org.projectodd.shimdandy.impl.ClojureRuntimeShimImpl.invoke(ClojureRuntimeShimImpl.java:109)
	at org.projectodd.shimdandy.impl.ClojureRuntimeShimImpl.invoke(ClojureRuntimeShimImpl.java:102)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at clojure.lang.Reflector.invokeMatchingMethod(Reflector.java:93)
	at clojure.lang.Reflector.invokeInstanceMethod(Reflector.java:28)
	at boot.pod$eval_in_STAR_.invoke(pod.clj:440)
	at boot$dev_backend$fn__489$fn__490.invoke(boot.clj:159)
	at boot$dev_backend$fn__476$fn__477.invoke(boot.clj:155)
	at boot.core$run_tasks.invoke(core.clj:938)
	at boot.core$boot$fn__933.invoke(core.clj:948)
	at clojure.core$binding_conveyor_fn$fn__4676.invoke(core.clj:1938)
	at clojure.lang.AFn.call(AFn.java:18)
	at java.util.concurrent.FutureTask.run(FutureTask.java:266)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:748)
```

## Tooling

This project uses [boot](http://boot-clj.com/)
<img width="24px" height="24px" src="https://github.com/boot-clj/boot-clj.github.io/blob/master/assets/images/logos/boot-logo-3.png" alt="Boot Logo"/>
and could not be more happy.

All the customization happens in `build.boot` and (rarely) in `dev/boot.clj`. The core of `build.boot` is the `boot/options` multi-method, which allows the developer to specify different configurations based on the `[flavor build-type]` vector.
It follows a simple rule: the returned map will have to contain keys that match the boot task you want to configure.

Thence a map like
```
{:repl {:init-ns 'dev
        :port 5055}
 :jar {:main 'tlog-exception-test.core
       :file "tlog-exception-test-standalone.jar"}
 :aot {:all true}}
```
will setup the `repl`, `jar` and `aot` boot built-in tasks respectively.

All of the boot commands accept a `-f|--flavor` and `-t|--type` that defines which option map the boot tasks will work against. Reasonable defaults are employed when these are missing, feel free to run `boot dev|build|test -h` for additional help.

### Interactive workflow

`boot dev` will launch:

 - A Clojure nRepl on port 5055, the backend server itself exposed on port 3000

You can explore `env/dev/src/dev.clj` for getting acquainted with the system management tools. You will notice that there is no `(dev/reset)` or call to `clojure.tools.namespace` in there.
This is because Cursive already [has an opaque shortcut](https://cursive-ide.com/userguide/repl.html) to it while in [cider](https://github.com/clojure-emacs/cider) the following (configurable) function will give you an error-free reloaded worflow:

```
(defcustom cider-repl-refresh-after 'dev/go
  "Symbol of a function that will be executed after
  clojure.tools.namespace.repl/refresh."
  :type 'string
  :group 'cider)

(defun cider-repl-refresh ()
  (interactive)
  (save-some-buffers)
  (with-current-buffer (cider-current-repl-buffer)
    (goto-char (point-max))
    (insert (concat "(require 'clojure.tools.namespace.repl) "
                    "(clojure.tools.namespace.repl/refresh :after "
                    "'" (symbol-name cider-repl-refresh-after) ")"))
    (cider-repl-return)))
```

### Build

For building the final artifact you need:

`boot build -t prod|dev target`

The artifact will be materialized in the `target` folder if you append the `target` task. Note that you *have to* specify a final task in order to dump the artifact but you can specify any task for deployment (e.g.: [`sync-bucket`](https://github.com/confetti-clj/confetti#syncing-your-site)). The `build` command defaults to `prod` when called with no arguments.

The backend, which will exit immediately as it is "empty", can be started with:

`java -jar tlog-exception-test-standalone.jar tlog-exception-test.core`


#### Logging

Logging is based on [clojure.tools.logging](https://github.com/clojure/tools.logging) and [Apache Log4j 2](https://logging.apache.org/log4j/2.x/).
If you need to control the configuration from a custom file you can launch the executable with:

`java -Dlog4j.configurationFile "your log4j2.xml path" -jar tlog-exception-test-standalone.jar tlog-exception-test.core`

Otherwise the provided configuration file is `env/prod|dev|test/resources/log4j2.xml`.

#### Config

The config files are in `env/dev/resources/config.edn` (mainly server-side) and `env/dev/src/tlog_exception_test/env.cljc` (potentially shared between Clojure and ClojureScript). You can see the merged content with `(dev/config)`:

```
{:greeting "Tlog-exception-test"
 :version "0.1.0-SNAPSHOT"
 :build :prod|:dev|:test
 :logging {:level :debug}
 ...}
```


An additional file is automatically merged: the file found at the location set using the `conf` system property. For instance, to merge a `external-config.edn` file, call your jar with:

`java -Dconf="../somepath/external-config.edn" -jar tlog-exception-test-standalone.jar tlog-exception-test.core`

This works in `profile.boot` as well:

```
(env/def
  conf "../somepath/external-config.edn"
  ...)
```

*Note:* `cprop` will merge environment variables and system properties if and only if they are already present in `config.edn` or `env.cljc` with the right nesting *and* syntax.

#### Testing

Backend tests use `clojure.test` and can be triggered with:

`boot test -f backend` or `boot watch test -f backend` (for auto testing)

More succintely, the shortcut `boot test` triggers all the tests in your project.

## Other Resources

 * [boot docs](https://github.com/boot-clj/boot/tree/master/doc)
 * [log4j2 configuration](https://logging.apache.org/log4j/2.x/manual/configuration.html)
 * [cprop env vars syntax](https://github.com/tolitius/cprop#speaking-env-variables)
 * [cprop system prop syntax](https://github.com/tolitius/cprop#system-properties-cprop-syntax)
 * [doo environments](https://github.com/bensu/doo#setting-up-environments)
