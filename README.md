# Intro

A prototype Clojure library which contains a potential new design for the iPlant 
<abbr title="Discovery Environment">DE</abbr> 
This library is built with [Compojure-api](https://github.com/metosin/compojure-api) and 
[Liberator](http://clojure-liberator.github.io/liberator/).

To quickly see this in action, run the command `> lein ring server`. 

## Compojure-api
Compojure-api provides enhanced [Compojure](https://github.com/weavejester/compojure) which allow
 RESTful endpoints to be documented in place. Furthermore, Compojure-api uses [Schema](https://github.com/Prismatic/schema) 
 to define query parameters, body parameters, and JSON output. These schema definitions are used 
 to build the documentation, but they also provide schema validation for the endpoint.
 
## Liberator
> Liberator is a library which helps you expose your data as resources while automatically 
> complying with all the relevant requirements of the HTTP specification. 

PATCH is currently not supported in liberator v0.11.0, but will be in v0.12.0. 
An issue was opened asking when 0.12.0 will be released, 
[here](https://github.com/clojure-liberator/liberator/issues/135).

# Useful Links
* HTTP specification
    * [HTTP/1.1: Message Syntax and Routing](http://tools.ietf.org/html/rfc7230)
    * [HTTP/1.1: Semantics and Content](http://tools.ietf.org/html/rfc7231)
    * [HTTP/1.1: Conditional Requests](http://tools.ietf.org/html/rfc7232)
    * [HTTP/1.1: Range Requests](http://tools.ietf.org/html/rfc7233)
    * [HTTP/1.1: Caching](http://tools.ietf.org/html/rfc7234)
    * [HTTP/1.1: Authentication](http://tools.ietf.org/html/rfc7235)
    * [PATCH Method for HTTP](http://tools.ietf.org/html/rfc5789)
    * [LINK & UNLINK Methods _(not RFC yet)_](http://tools.ietf.org/html/draft-snell-link-method-01)
* [Compojure-api](https://github.com/metosin/compojure-api) 
* [Compojure](https://github.com/weavejester/compojure)
* [Schema](https://github.com/Prismatic/schema) 
* [Liberator](http://clojure-liberator.github.io/liberator/)

