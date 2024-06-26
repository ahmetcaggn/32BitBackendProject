FROM openjdk:17-alpine
LABEL maintainer = "ahmetcaggn"
WORKDIR /app
EXPOSE 8100
COPY target/Sales-0.0.1-SNAPSHOT.jar Sales.jar
ENTRYPOINT ["java", "-jar", "Sales.jar"]