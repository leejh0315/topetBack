FROM openjdk:17-jdk-slim
RUN mkdir /app
COPY ./docker/topetBack-0.0.1-SNAPSHOT.jar /app/app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
