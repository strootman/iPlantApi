(ns iplant-api.routes.domain.apps-domain
  (:require [schema.core :as s]
            [liberator.core :as lib]
            [ring.swagger.schema :as ss]))

;; Schemas
(s/defschema App {:id (ss/field Long {:description "unique identifier"})
                  :name (ss/field String {:description "user friendly app name"})})

(s/defschema AppSearchQuery {(s/optional-key :name) (ss/field String {:description "app name"})
                             (s/optional-key :user) (ss/field String {:description "apps integrated by the given user"})
                             (s/optional-key :email) (ss/field String {:description "apps whose integrator has the given email"})
                             (s/optional-key :search) (ss/field String {:description "search for apps containing the given terms"})})

(def MetadataElementTypes (s/enum :components :formats :info_types
                            :property_types :rule_types :value_types
                            :data_sources :tool_types))

(s/defschema AppMetadataQuery {:type (ss/field MetadataElementTypes {:description "supported metadata types"})})

(s/defschema SearchQuery {:q String})

(s/defschema NewApp (dissoc App :id))

(s/defschema AppTool {:id Long
                      :name String})
(s/defschema NewAppTool (dissoc AppTool :id))

(s/defschema SingleApp {:id String
                        :name String})

(s/defschema PatchApp {(s/optional-key :name) (ss/field s/Str {:description "the app name"})
                       (s/optional-key :rating) (ss/field s/Int {:description "the app rating"
                                                              :minimum "1" :maximum "5"})})

;; Sample prototype functionality
(defonce id-seq (atom 0))
(defonce apps (atom (array-map)))
(defonce tools (atom (array-map)))

(defn get-app [id] (@apps id))
(defn get-apps [] (-> apps deref vals reverse))
(defn deleteApp! [id] (swap! apps dissoc id) nil)
(defn addApp! [new-app]
  (let [id (swap! id-seq inc)
        app (ss/coerce! App (assoc new-app :id id))]
    (swap! apps assoc id app)
    app))
(defn updateApp! [app]
  (let [app (ss/coerce! App app)]
    (swap! apps assoc (:id app) app)
    (get-app (:id app))))

(defn get-tools [] (-> tools deref vals reverse))
(defn addTool! [new-tool]
  (let [id (swap! id-seq inc)
        tool (ss/coerce! AppTool (assoc new-tool :id id))]
    (swap! tools assoc id tool)
    tool))

;; Liberator Resources

;; For /apps/:id endpoint
(lib/defresource single-app-resource [id]
  :allowed-methods [:get :put :patch :delete]
  :available-media-types ["application/json"]
  :authorized? true ;; Check oauth?
  :exists? (fn [ctx]
             (if-let [app (get-app id)] {:entity app}))
  :can-put-to-missing? false
  :respond-with-entity? false
  :handle-created (fn [ctx]
                    (get-app id))
  :handle-ok :entity
  :as-response (fn [d ctx] {:body d}))

;; /apps endpoint
;; TODO Need to decide how to treat searches which may actually be headers
(lib/defresource apps-resource []
  :allowed-methods [:get :post]
  :available-media-types ["application/json"]
  :authorized? true ;; Check oauth?
  :allowed? true ;; If authorized, check if user can perform action
  :post! (fn [ctx]
           (if-let [newapp (addApp! (get-in ctx [:request :body]))] {:entity newapp}))
  :handle-ok (fn [ctx] ;; will need to check query params here. i.e. (get-in ctx [:request :query-params])
               (get-apps))
  :handle-created :entity
  :as-response (fn [d ctx]
                 {:body d}))

;; Initial sample Prototype Data
(when (empty? @apps)
  (addApp! {:name "App One"})
  (addApp! {:name "App Two"}))

(when (empty? @tools)
  (addTool! {:name "Tool one"})
  (addTool! {:name "Tool two"}))
