# Library application

![CI status](https://github.com/xpinjection/test-driven-spring-boot/actions/workflows/maven.yml/badge.svg)

Sample Spring Boot cloud-native application written in TDD style.

## Local run

The easiest way to run application locally is to use _LocalLibraryApplication_ class. It will run all needed dependencies in the Docker containers and configure application to use them automatically.

## Configuration

To enable Actuator endpoints admin profile has to be activated with proper secrets provided via system properties or other suitable external configuration option (VM options):

> _-Dspring.security.user.name=admin -Dspring.security.user.password=xpinjection -Dspring.profiles.active=dev,admin_

## Consumer-driven contracts

[Pact](https://docs.pact.io/) is used to describe and manage REST API contracts.

Sample instance of the Pact Broker in Docker could be started with `/pact/docker-compose.yaml`. It starts the Pact Broker and the PostgreSQL database as dependency. Data is not stored on volumes, so will be cleaned after restart.

Pact integration could be enabled during tests execution with `pactbroker.enabled` system property.

Use system variable `pact.verifier.publishResults` to control pact verification results publishing to the Pact Broker. Pact Broker configuration is located in `pom.xml` and could be overridden with system properties as well.

System properties `pact.provider.version` and `pact.provider.branch` should be used to pass correct version of the application and git branch for tracking in the Pact Broker.

---

Run Configuration:

![Run Configuration](/library-app-run-configuration.png)

Prepare artifact with:

    mvn clean package -Dmaven.test.skip=true

Build image:

    docker build . -t lelyak/library:0.1.0

In case you use k3d cluster - you need to import docker image to the cluster first:

    k3d image import lelyak/library:0.1.0 -c mycluster
