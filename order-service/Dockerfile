FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY build/libs/*.jar app.jar
EXPOSE 12001
ENTRYPOINT ["java", "-jar", "/app/app.jar"]