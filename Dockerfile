FROM openjdk:17-jdk-slim
COPY *SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
