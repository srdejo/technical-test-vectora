FROM eclipse-temurin:21-jdk as builder
WORKDIR /app

COPY . .
RUN ./gradlew clean build -x test

FROM eclipse-temurin:21-jre
WORKDIR /app

EXPOSE 8081

COPY --from=builder /app/build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
