                           Vote Aggregation System
                           
This is a simple java project to count the votes of a music competition 

The projects uses below thins

1. gradle as build tool
2. Spring as dependency injection framework
3. In memory h2 db for story and aggregating the votes

The only pre-requisite for this application is have docker installed in your laptop 
and connectivity to the internet to fetch required dependencies

To run the application

1. create a directory
2. git pull https://github.com/shony1989/pega-vote-bank.git
3. cd pega-vote-bank
4. docker-compose up
5. the API is available to serve on URL http://localhost:8080

API documentation is available at http://localhost:8080/swagger-ui.html

Docker file is a multistage build , building the artifact and creating an image

