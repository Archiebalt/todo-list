FROM openjdk:17.0.2-jdk-slim-buster AS builder
WORKDIR /build
COPY pom.xml .
COPY src ./src
RUN mvn clean package

FROM bellsoft/liberica-openjdk-alpine:17-cds
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "app.jar" ]