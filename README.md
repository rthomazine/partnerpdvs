# partnerpdvs app

## Requirements

- Java 11 and above
- Maven 3.6 and above
- Docker + docker-compose
- Web browser

## Building and testing the application localy

Go to the project root directory and run the MongoDB container:
``` 
docker-compose up -d
```
Build the application
``` 
mvn clean package -Dmaven.test.skip=true
```
Run the application
``` 
java -jar target/partnerpdvs-<build-version>.jar
```
Open the URL below in a web browser
``` 
http://localhost:8080/swagger-ui.html
```

## Building and testing in a docker container

Go to the project root directory and build the application if it is not built yet
``` 
mvn clean package -Dmaven.test.skip=true
```
Build the docker image
```
docker build -t partnerspdvs .
```
Run it as a docker container
```
docker run --name partnerspdvs --rm partnerspdvs:latest
```
Discover the IP address to open on web browser
```
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}'  partnerspdvs
```
Open the URL below in a web browser
``` 
http://<discovered-IP-address>:8080/swagger-ui.html
```
