FROM openjdk:17-jdk-slim
RUN apt-get update && \
    apt-get install -y maven
WORKDIR /app
COPY . /app
RUN mvn clean package -DskipTests
CMD ["java", "-jar", "target/flashcard-0.0.1.jar"]