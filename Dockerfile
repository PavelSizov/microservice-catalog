FROM openjdk:8-jre-alpine
EXPOSE 8080
COPY /target/catalog-0.0.1-SNAPSHOT.jar catalog.jar
ENTRYPOINT ["java", "-jar", "catalog.jar"]