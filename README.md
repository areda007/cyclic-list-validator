[![CI workflow](https://github.com/areda007/cyclic-list-validator/actions/workflows/ci.yml/badge.svg)](https://github.com/areda007/cyclic-list-validator/actions/workflows/ci.yml) [![Coverage](.github/badges/jacoco.svg)](https://github.com/areda007/cyclic-list-validator/actions/workflows/ci.yml)

# cyclic-list-validator

This project is providing an API endpoint that would allow you to check if array is a cyclic or not. 


## Table of Contents
* [General Info](#general-information)
* [Technologies Used](#technologies-used)
* [Features](#features)
* [Setup](#setup)
* [Demo](#demo)
* [Continuous Integration Flow](#continuous-integration-flow)
* [Room for Improvement](#room-for-improvement)



## General Information
This project covers part 1 and part 2 as per the requirements. 

Regarding part 2, I have put consumer and producer in the same project for sake of simplicity but they are still communicates through a separate rabbit MQ service.


## Technologies Used
- Java 11.
- Spring Boot.
- RabbitMQ.
- Docker and Docker Compose.
- Spring Actuator for service observability (Health endpoints).
- Open API Specs 3.0 (Swagger).
- Test Containers for RabbitMQ integration tests.
- JUnit5 and Mockito
- Github Actions for Continous Integration workflow.


## Features
List the ready features here:
- Testing part 1 in the task by checking our api-doc link and try API from the provided interface. 
	- Go to http://localhost:8080/api-doc
- We also provide a post endpoint (/pushMessage) in api-doc that acts our testing producer for pushing messages to RabbitMQ.
	- All messages pushed by that endpoint will be picked up by the consumer. You can check the container logs to verify that.


## Setup
The only required dependency for installing that project is Docker. Please [download](https://docs.docker.com/get-docker/) if you don't have. Also, download our [docker-compose-file](https://raw.githubusercontent.com/areda007/cyclic-list-validator/master/compose.yml).

After docker setup and downloading compose file, now you can start the services by running the following

```
> cd ${compose file directory}
> docker compose up
```

Please note that you do not need checkout and build the project (Thanks to our CI workflow). The docker-compose-file is utilizing public images pushed by CI workflow.


## Demo
![Example screenshot](./img/screenshot.png)
<!-- If you have screenshots you'd like to share, include them here. -->


##Continuous Integration Flow

Once we push any change to master or we merge any request. This will trigger our CI workflow which is responsible for 
- Checkout and build latest pushed code to generate java archive file (jar).
	- build process includes running all test cases unit and integration tests (done by test containers).
- Generating code coverage report badge.
- Build Docker Image.
- Push Docker Image to Docker Hub public repository.

## Room for Improvement
I tried to do my best but as usual there's always room for improvement.

Room for improvement:
- Having a response queue in place where the producer will be getting results of his submitted messages.

- Setup Kuberenetes cluster.

- Integrate with monitoring tools for better service observability.

To do:
- Fixing integration test for messageConsumer service for better coverage for all scenarios.