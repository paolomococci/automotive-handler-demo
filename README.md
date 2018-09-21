# automotive-handler-demo
Demo of the RESTful web service with Spring Boot in the Kotlin programming language

# I get default items saved with LoadVehicleItems.kt class from vehicles table:

$ curl -v -i http://127.0.0.1:9090/api/vehicles

# I create a new item in vehicles table:

$ curl -H "Content-Type:application/json" --data '{"brand":"Minivan Inc.","name":"Tour 1800"}' http://127.0.0.1:9090/api/vehicles

# I update item four from vehicles table:

$ curl -v -i -X PUT -H "Content-Type:application/json" --data '{"brand":"Maxivan Inc.","name":"GT 2500"}' http://127.0.0.1:9090/api/vehicles/4

# I partial update item four from vehicles table:

$ curl v -i -X PATCH -H "Content-Type:application/json" --data '{"name":"GT 2000"}' http://127.0.0.1:9090/api/vehicles/4

# I delete item four from the vehicles table:

$ curl -v -i -X DELETE http://127.0.0.1:9090/api/vehicles/4
