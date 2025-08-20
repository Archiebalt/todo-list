FROM eclipse-temurin:17-jre-alpine

RUN addgroup -g 1000 spring-app && adduser -u 1000 -G spring-app -D spring-app
USER spring-app:spring-app

WORKDIR /opt/workspace
COPY target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/opt/workspace/app.jar"]