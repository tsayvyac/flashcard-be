#!/bin/bash

# Load environment variables from .env file
set -o allexport
source ../.env
set -o allexport
cd ../

# Build the application using Maven
mvn clean package -DskipTests

# Run the Java Spring Boot application
java -jar "target/$(ls target | grep '.jar$' | head -n 1)" \
      -Dspring.datasource.url=${DATASOURCE_URL} \
      -Dspring.datasource.username=${DATASOURCE_USERNAME} \
      -Dspring.datasource.password=${DATASOURCE_PASSWORD} \
      -Dsecurity.jwt.secret-key=${JWT_SECRET_KEY} \
      -Dsecurity.jwt.expiration=${JWT_EXPIRATION} \
      -Dserver.port=${SERVER_PORT} \
      -Dserver.servlet.context-path=${CONTEXT_PATH} \
      -Dapp.async.core-pool-size=${CORE_POOL_SIZE} \
      -Dapp.async.max-pool-size=${MAX_POOL_SIZE} \
      -Dapp.async.queue-capacity=${QUEUE_CAPACITY}