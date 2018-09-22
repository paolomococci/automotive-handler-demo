# automotive-handler-demo
Demo of the RESTful web service with Spring Boot in the Kotlin programming language

##I get default items saved with LoadVehicleItems.kt class from vehicles table:
```
$ curl -v -i http://127.0.0.1:9090/api/vehicles
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> GET /api/vehicles HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 200 
HTTP/1.1 200 
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Fri, 21 Sep 2018 06:31:40 GMT
Date: Fri, 21 Sep 2018 06:31:40 GMT

< 
{
  "_embedded" : {
    "vehicles" : [ {
      "brand" : "Supercars Inc.",
      "name" : "GTO 2000",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vehicles/1"
        },
        "vehicles" : {
          "href" : "http://127.0.0.1:9090/api/vehicles"
        }
      }
    }, {
      "brand" : "Supercars Inc.",
      "name" : "GTO 3000",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vehicles/2"
        },
        "vehicles" : {
          "href" : "http://127.0.0.1:9090/api/vehicles"
        }
      }
    }, {
      "brand" : "Supercars Inc.",
      "name" : "GTO 5000",
      "_links" : {
        "self" : {
          "href" : "http://127.0.0.1:9090/api/vehicles/3"
        },
        "vehicles" : {
          "href" : "http://127.0.0.1:9090/api/vehicles"
        }
      }
    } ]
  },
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vehicles"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}
```

##I __create__ a new item in vehicles table:
```
$ curl -H "Content-Type:application/json" --data '{"brand":"Minivan Inc.","name":"Tour 1800"}' http://127.0.0.1:9090/api/vehicles
{
  "brand" : "Minivan Inc.",
  "name" : "Tour 1800",
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vehicles/4"
    },
    "vehicles" : {
      "href" : "http://127.0.0.1:9090/api/vehicles"
    }
  }
}
```

##I __read__ a new item in vehicles table:
```
$ curl -v -i http://127.0.0.1:9090/api/vehicles/4
```

##I __update__ item four from vehicles table:
```
$ curl -v -i -X PUT -H "Content-Type:application/json" --data '{"brand":"Maxivan Inc.","name":"GT 2500"}' http://127.0.0.1:9090/api/vehicles/4
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PUT /api/vehicles/4 HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 41
> 
* upload completely sent off: 41 out of 41 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vehicles/4
Location: http://127.0.0.1:9090/api/vehicles/4
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Fri, 21 Sep 2018 06:53:09 GMT
Date: Fri, 21 Sep 2018 06:53:09 GMT

< 
{
  "brand" : "Maxivan Inc.",
  "name" : "GT 2500",
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vehicles/4"
    },
    "vehicles" : {
      "href" : "http://127.0.0.1:9090/api/vehicles"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}
```

##I __partial__ __update__ item four from vehicles table:
```
$ curl v -i -X PATCH -H "Content-Type:application/json" --data '{"name":"GT 2000"}' http://127.0.0.1:9090/api/vehicles/4
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> PATCH /api/vehicles/4 HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> Content-Type:application/json
> Content-Length: 18
> 
* upload completely sent off: 18 out of 18 bytes
< HTTP/1.1 201 
HTTP/1.1 201 
< Location: http://127.0.0.1:9090/api/vehicles/4
Location: http://127.0.0.1:9090/api/vehicles/4
< Content-Type: application/hal+json;charset=UTF-8
Content-Type: application/hal+json;charset=UTF-8
< Transfer-Encoding: chunked
Transfer-Encoding: chunked
< Date: Fri, 21 Sep 2018 07:21:48 GMT
Date: Fri, 21 Sep 2018 07:21:48 GMT

< 
{
  "brand" : "Minivan Inc.",
  "name" : "GT 2000",
  "_links" : {
    "self" : {
      "href" : "http://127.0.0.1:9090/api/vehicles/4"
    },
    "vehicles" : {
      "href" : "http://127.0.0.1:9090/api/vehicles"
    }
  }
* Connection #0 to host 127.0.0.1 left intact
}
```
##I __delete__ item four from the vehicles table:
```
$ curl -v -i -X DELETE http://127.0.0.1:9090/api/vehicles/4
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to 127.0.0.1 (127.0.0.1) port 9090 (#0)
> DELETE /api/vehicles/4 HTTP/1.1
> Host: 127.0.0.1:9090
> User-Agent: curl/7.59.0
> Accept: */*
> 
< HTTP/1.1 204 
HTTP/1.1 204 
< Date: Fri, 21 Sep 2018 07:12:17 GMT
Date: Fri, 21 Sep 2018 07:12:17 GMT

< 
* Connection #0 to host 127.0.0.1 left intact
```
