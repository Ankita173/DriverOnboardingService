# Driver Onboarding Service

### Reference Documentation
This application aims to cater to a driver onboarding module for a ride sharing app.

### Tech stack
* Backend - Java
* Framework - Springboot
* Build - Maven
* Deployment/Hosting: Docker, Kubernetes(Minikube)
* Database: PosrgreSQL

### How to run the project
There is a run.sh script at the root which perform below execution steps:
* Perform maven build
* Build docker image
* Push the image to docker hub
* Deleting app deployment
* Apply Kube config, secret, services, deployments
* List kube pods

### SWAGGER-UI
http://localhost:8080/v1/rider/swagger-ui/index.html#/

### API contracts 
base-url: v1/rider/
* POST /driver/register : To register a new driver
* POST /driver/login : To verify login of a driver
* GET /driver : Get all drivers 
* GET /driver/{id} : Get Particular driver details
* PUT /driver : Update driver details
* PUT /driver/{id}/status : Update driver status
* DELETE /driver : Delete the driver
* POST /driver/document/{id} : Insert driver document
* GET /driver/register/{id}/{filename} : Download the driver document

### Entities
* DriverCredential
  > driverId long pk
  > username String
  > password String
  > timeCreated timestamp 
  > timeUpdated timestamp

* Driver
  > driverId long pk
  > username String
  > firstName String
  > lastName String
  > phoneNo long
  > driverLicense String
  > address String
  > zipCode long
  > status enum (Accepted,documentCollection,verification,shippingDevice,rejected,active,busy,inactive)
  > timeCreated timestamp
  > timeUpdated timestamp

* DriverDocument
  > driverId long pk
  > docuName Stirng pk
  > docuPath String
  > timeCreated timestamp



