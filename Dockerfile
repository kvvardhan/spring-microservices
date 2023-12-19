FROM eclipse-temurin:17

LABEL maintainer="vishnuvardhan1989@gmail.com"

WORKDIR /app

COPY target/Spring-Microservices-0.0.1-SNAPSHOT.jar /app/Spring-Microservices.jar

ENTRYPOINT ["java", "-jar", "Spring-Microservices.jar"]