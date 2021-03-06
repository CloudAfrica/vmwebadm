(ns server.vm
  (:use [server.utils :only [clj->js log]])
  (:require [cljs.nodejs :as node]))

(defn wrap-callback [callback]
  (fn [error result]
    (callback (js->clj error) (js->clj result))))

(def VM
  (node/require "VM"))

;  "start(uuid, extra, callback)"
(defn start
  ([uuid callback]
     (start uuid {} callback))
  ([uuid extra callback]
      (.start
       VM
       (clj->js uuid)
       (clj->js extra)
       (wrap-callback callback))))

;  "stop(uuid, options={[force=true]}, callback)"
(defn stop
  ([uuid callback]
     (stop uuid {:force false} callback))
  ([uuid options callback]
      (.stop
       VM
       (clj->js uuid)
       (clj->js options)
       (wrap-callback callback))))

;  "info(uuid, [types=[\"all\"]] callback)"
(defn info
  ([uuid callback]
     (info uuid ["all"] callback))
  ([uuid types callback]
        (.info
         VM
         (clj->js uuid)
         (clj->js types)
         (wrap-callback callback))))

;  "lookup([match={}], callback)"
(defn lookup
  ([callback]
     (lookup {} {:full true} callback))
  ([search opts callback]
     (.lookup
      VM
      (clj->js
       (if (string? search)
         {"uuid" search}
         search))
      (clj->js opts)
      (wrap-callback callback))))

;  "console(uuid, callback)"
(defn console [uuid callback]
  (.console
   VM
   (clj->js uuid)
   (wrap-callback callback)))

;  "create(properties, callback)"
(defn create [properties callback]
  (.create
   VM
   (clj->js properties)
   (wrap-callback callback)))

;  "delete(uuid, callback)"
(defn del [uuid callback]
  (js/server.vm.VM.delete
   (clj->js uuid)
   (wrap-callback callback)))

;  "load([zonename|uuid], callback)"
(defn load [uuid callback]
  (.load
   VM
   (clj->js uuid)
   (wrap-callback callback)))

;  "reboot(uuid, options={[force=true]}, callback)"
(defn reboot
  ([uuid callback]
     (reboot uuid {:force true} callback))
  ([uuid options callback]
     (.reboot
      VM
      (clj->js uuid)
      (clj->js options)
      (wrap-callback callback))))

;  "sysrq(uuid, req=[nmi|screenshot], options={}, callback)"
(defn sysrq
  ([uuid req callback]
     (sysrq uuid req {} callback))
  ([uuid req options callback]
     (.sysrq
      VM
      (clj->js uuid)
      (clj->js req)
      (clj->js options)
      (wrap-callback callback))))

;  "update(uuid, properties, callback)"
(defn update [uuid properties callback]
  (.update
   VM
   (clj->js uuid)
   (clj->js properties)
   (wrap-callback callback)))

;(defn flatten [vmobj])
;* flatten(vmobj, key)
