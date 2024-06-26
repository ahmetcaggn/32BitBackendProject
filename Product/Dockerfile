FROM openjdk:17-alpine
LABEL maintainer = "ahmetcaggn"
WORKDIR /app
EXPOSE 8000
COPY target/Product-0.0.1-SNAPSHOT.jar Product.jar
ENTRYPOINT ["java", "-jar", "Product.jar"]