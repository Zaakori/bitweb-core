FROM openjdk:17-jdk-slim-buster
WORKDIR /app

COPY build/libs/Core-0.0.1-SNAPSHOT.jar build/

WORKDIR /app/build
ENTRYPOINT java -jar Core-0.0.1-SNAPSHOT.jar