FROM openjdk:17-alpine
LABEL maintainer = "ahmetcaggn"
WORKDIR /app
EXPOSE 8300
COPY target/report-0.0.1-SNAPSHOT.jar report.jar
ENTRYPOINT ["java", "-jar", "report.jar"]