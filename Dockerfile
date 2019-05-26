FROM openjdk:8-alpine
ADD target/docker-spring-boot.jar docker-spring-boot.jar
WORKDIR /
COPY ./ /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "docker-spring-boot.jar"]