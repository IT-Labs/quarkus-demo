# Quarkus and GraalVM workshop

## An introductory Workshop on Quarkus and GraalVM

In this workshop we will show how to effectively leverage the supersonic and subatomic framework Quarkus to make fast and reliable java applications.

See the diagram below for the architecture of the app we will be building.

![alt text](https://github.com/IT-Labs/Quarkus/blob/main/img/overview_app_jvm.png)
![alt text](https://github.com/IT-Labs/Quarkus/blob/main/img/overview_app_binary.png)

## Prerequisites

- **Docker** ->  https://www.docker.com/
- **GraalVM** -> https://www.graalvm.org/downloads/
- **Java**: -> https://www.oracle.com/java/technologies/downloads/
- **Favourite IDE**

# Step 1 - Bootstrap our application
We have multiple ways of bootstrapping our application, using Intellij, Maven/Gradle or using quarkus initializer

1.	Navigate to https://code.quarkus.io/.
2.	Choose Group name and artifact name.
3.	Choose Build Tool (in this project Maven will be used).
4.	Choose Java version (in this project Java 21 will be used).
5.	Select the following dependencies.
      -  RESTEasy Classic Jackson
      -  Agroal - Database connection pool
      -  JDBC Driver - PostgreSQL
      -  RESTEasy Classic
      -  Hibernate ORM with Panache
6.	Under Generate your application - choose download as a zip.


# Step 2 - Develop our application

1.	Unzip the app and import it in the IDE.
2.	We will be using postgres as part of docker - create a docker file for postgres container(refer to docker-postgres.yml file)
      Starting the container is done with the following command `docker-compose -f "docker-postgres.yml" up -d`
3. Write a seed script to seed some data when the app is started (refer to seed.sql file)
4. Configure your app to connect to the database (refer to application.properties file which is located under resources package)
5. Create our Movie entity with all the information we need (refer to Movie.java file which is located under entity package)
6. Create the Movie repository (refer to MovieRepository.java file which is located under repository package)
7. Create the Movie resource (refer to MovieResource.java file which is located under web package)

# Step 3 - Tests
1.	For tests refer to GreetingResourceTest.java file and MovieResourceTest.java files located under test package

# Important commands for packaging, building and starting the app
Adding Docker Container extension for building linux executables  
`./mvnw quarkus:add-extension -Dextensions="container-image-docker"`

## Running the application in dev mode
You can run your application in dev mode that enables live coding using: `./mvnw compile quarkus:dev`
Or you can run it in your IDE

## Packaging and running the application
The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```
The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:
```shell script
./mvnw package -Dquarkus-profile=stage -Pnative
```
This will create native executable with profile stage (depending on your operating system) and this executable will be located under target as `target/*-runner`
You don't need anything besides your operating system to run it (for windows this is an exe file)

## Packaging and running app under Docker
Build it as jvm image
`./mvnw package "-Dquarkus.container-image.build=true" "-Dquarkus.container-image.tag=jvm-panache-hibernate"`

Starting it (change the m.t with your docker repository)
`docker run -i --rm -p 8080:8080 --network=quarkus-network m.t/quarkus-app:jvm-panache-hibernate`

Build it as native image
`./mvnw package "-Dquarkus.container-image.build=true" "-Dquarkus.package.type=native" "-Dquarkus.native.container-build=true" "-Dquarkus.container-image.tag=native-with-panache"`

Starting it (change the m.t with your docker repository)
`docker run -i --rm -p 8080:8080 --network=quarkus-network m.t/quarkus-app:native-panache-hibernate`
