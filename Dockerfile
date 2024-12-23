# Build stage
FROM openjdk:17-jdk-slim AS build
WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && \
    apt-get install -y maven && \
    mvn clean package -DskipTests

# Run stage
FROM openjdk:17-jdk-slim

RUN groupadd --system appgroup && useradd --system --create-home --gid appgroup appuser

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

RUN chown -R appuser:appgroup /app
USER appuser

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]