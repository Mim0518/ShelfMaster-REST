FROM openjdk:21-jdk-headless
LABEL authors="Guillermo Moreno Rivera"
ARG JAR_FILE=target/*.jar
COPY ./target/Biblioteca-0.0.2-RELEASE.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]