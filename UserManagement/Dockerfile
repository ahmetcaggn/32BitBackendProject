FROM openjdk:17-alpine
LABEL maintainer = "ahmetcaggn"
WORKDIR /app
EXPOSE 8200
COPY target/UserManagement-0.0.1-SNAPSHOT.jar UserManagement.jar
ENTRYPOINT ["java", "-jar", "UserManagement.jar"]