(ns iplant-api.core
  (:require [compojure.api.sweet :refer :all]
            ;; Pre-packaged middleware which is automatically included with "defapi"
            [compojure.api.middleware :refer [api-middleware]]
            ;; This middleware is used for debugging liberator decision traces
            [liberator.dev :as lib]
            [iplant-api.routes.apps :as apps]
            [iplant-api.routes.domain.apps-domain :refer :all]))


(defapi iplant
  (middlewares [(lib/wrap-trace :header)] ;; Add "wrap-trace" middleware to add liberator debugging
    (swagger-docs "/api/api-docs"     ;; Include this to serve up swagger docs on the given path
      :title "iPlant API"
      :description "Documentation for the iPlant REST API"
      :apiVersion "0.0.1")
    (swagger-ui "/")                  ;; Include this to serve up a default swagger UI
    (swaggered "apps"                 ;; Defines a section in the swagger markup,
                                      ;; essentially a set of endpoints
      :description "apps endpoint"
      apps/base-routes
      apps/apps-id-routes)))

