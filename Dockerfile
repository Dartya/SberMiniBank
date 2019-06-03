FROM openjdk:8-alpine
WORKDIR /
COPY ./ /
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "alexpit_sbermini.jar"]