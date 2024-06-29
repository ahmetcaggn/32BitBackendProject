FROM openjdk:17-alpine
LABEL maintainer = "ahmetcaggn"
WORKDIR /app
EXPOSE 8400
COPY target/security-0.0.1-SNAPSHOT.jar security.jar
ENTRYPOINT ["java", "-jar", "security.jar"]