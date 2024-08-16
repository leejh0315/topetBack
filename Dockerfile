FROM openjdk:17-jdk-slim
COPY /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
