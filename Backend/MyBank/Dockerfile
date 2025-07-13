FROM maven:3-eclipse-temurin-21-alpine AS build

COPY src /app/src
COPY pom.xml /app

WORKDIR /app

ENV JWT_SECRET=${JWT_SECRET}

RUN mvn clean install

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /app/target/MyBank-0.0.1-SNAPSHOT.jar /app/app.jar

WORKDIR  /app

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]