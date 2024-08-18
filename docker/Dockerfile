FROM openjdk:17-jdk-slim
COPY ./docker/*-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
