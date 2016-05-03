SS-Apoorv
======
# Remember!!

`Code has been written in Java using following technologies:`
* Spring
* Spring-Data (MongoDB)
* Apache CXF (Rest APIs)
* Junit Test Cases


Setup your git client, maven, java, mongoDB on port 27017

Pull code from master repo and use dir `birdlibrary`.

For running test cases, please use following command

`mvn clean package`

* This will run 23 Test Cases

For running war on server, please use following command

`mvn tomcat:run`

* Server will start on default port 8080
* Endpoint to be used `http://www.localhost:8080/ss/services/v1/birds`
* Methods supported `GET,POST,DELETE`
* Headers: `Content-Type : application/json`, `Accept : application/json`


# Error handling
* WebApplicationException class handles all custom thrown errors which makes error response for user with respective HttpStatus code
* Mappers have been used to catch certain handled or any unhandled exception and return response with respective HttpStatus code

# Database
* MongoDB has been used with spring-data-mongodb and interaction is througg Mongo Repository

# Json Schema
* Maven schema parser plugin has been used which generates java pojo classes from respective schemas

# Test Cases
* Junit test cases has been written for service
* Test cases run with test application properties with DB saltside_test (MongoDB, port: 27017)

`Please reach at [apoorvmittal10@gmail.com] for any query `
