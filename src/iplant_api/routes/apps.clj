(ns iplant-api.routes.apps
  (:require [compojure.api.sweet :refer :all]
            [ring.util.http-response :as resp]
            [iplant-api.routes.domain.apps-domain :refer :all]))

(defroutes* base-routes
  (context "/apps" []
    ;; apps-resource
    (GET* "/" []
      ;:return [App]
      :query [query AppSearchQuery]
      :summary "returns the apps which match the search term"
      :notes "<b>TODO:</b> define search terms"
      (apps-resource))
    (POST* "/" []
      :return App
      :body [app NewApp {:description "the app to be added"}]
      :summary "add new app"
      (apps-resource))
    (GET* "/id" []
      :summary "retrieves all available apps by id only."
      (resp/ok (str "here is a list of app ids")))

    (GET* "/metadata" []
      :return AppMetadataQuery
      :query [metadataSearch AppMetadataQuery]
      :summary "retrieves the metadata associated with the given type parameter"
      (resp/ok metadataSearch))
    (GET* "/tools" []
      :return [AppTool]
      :summary "lists the tools which match the given query"
      (resp/ok (get-tools)))))

(defroutes* apps-id-routes
  ;; Operations on specific Apps
  (context "/apps/:id" []
    (GET* "/" []
      :path-params [id :- Long]
      :return App
      :summary "returns the app with the given id"
      :responseMessages [{:code 200
                          :message "TBD"
                          :responseModel App}]
      (single-app-resource id))
    (PUT* "/" []
      :path-params [id :- Long]
      :summary "replace contents of an app."
      (single-app-resource id))
    (PATCH* "/" []
      :path-params [id :- Long]
      :body [pApp PatchApp]
      :summary "performs partial update of an app"
      :notes "This can be used to make changes to an App's labels after the app has been
        published."
      (single-app-resource id))
    (DELETE* "/" []
      :path-params [id :- Long]
      (single-app-resource id))
    (GET* "/cli" [id]
      :summary "returns a command line representation of the given app"
      (resp/ok (str "some command line representation of the app with the given id")))
    (GET* "/data-objects" [id]
      :summary "returns the data objects associated with the given app"
      (resp/ok (str "a list of input data objects and a list of output data objects")))
    (GET* "/details" []
      :path-params [id :- Long]
      :return App
      :summary "returns further details of the given app"
      (resp/ok (get-app (id))))
    (GET* "/is-exportable" [id]
      :return Boolean
      :summary "returns a boolean indicating whether the given app is exportable or not"
      :notes "<b>NEED TO DETERMINE</b> what <i>\"exportable\"</i> means"
      (resp/ok false))
    (GET* "/is-publishable" [id]
      :return Boolean
      :summary "returns a boolean indicating whether the given app is publishable"
      (resp/ok false))
    (GET* "/tools" [id]
      :return [AppTool]
      :summary "a list of all the tools associate with this app"
      (resp/ok (get-tools)))
    (GET* "/ui" []
      :path-params [id :- Long]
      :return App
      :summary "a json representation of the given app which is used by the DE to render the UI for this app"
      :notes "<ul><li>define json representation</li><li>determine if this can be rolled into another endpoint and retrieved via content-negotiation</li></ul>"
      (resp/ok (get-app (id))))
    (POST* "/copy" [id]
      :return String
      :summary "creates a copy of the given app"
      (resp/ok (str "here is the id of the copy")))
    (POST* "/publish" [id]
      :summary "publishes the given app")
    (POST* "/rating" [id]
      :summary "sets the rating of the app for the current user"))

  )