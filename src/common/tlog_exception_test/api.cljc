(ns tlog-exception-test.api
  (:require
   #?@(:clj [[clojure.tools.logging :as log]])))

(defn spec
  [db]
  ["/" [["api" #?(:clj (fn [request] (log/info "/api hit")))]]])
