FROM openjdk:8
WORKDIR /usr/src/app
COPY Sealy.jar .
COPY .env .
ENTRYPOINT ["java", "-jar", "Sealy.jar"]