# test-driven-spring-boot

Sample Spring Boot applications written in TDD style.

The easiest way to run application locally is to use _LocalLibraryApplication_ class. It will run all needed dependencies in the Docker containers and configure application to use them automatically.

To enable Actuator endpoints admin profile has to be activated with proper secrets provided:

> _-Dspring.security.user.name=admin -Dspring.security.user.password=xpinjection -Dspring.profiles.active=dev,admin_

---

Execute buildpack image build:

    mvn spring-boot:build-image -Dmaven.test.skip=true

In case you use k3d cluster - you need to import docker image to the cluster first:

    k3d image import lelyak/boot-admin:0.1.0 -c mycluster

