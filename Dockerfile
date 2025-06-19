
# # Use a base image with JDK 17 (or whichever version you used)
# FROM eclipse-temurin:17-jdk

# # Set working directory
# WORKDIR /app

# # Copy the jar (you can also build in this Dockerfile if needed)
# COPY target/employeemanagementsystem-0.0.1-SNAPSHOT.jar app.jar

# # Copy the truststore (used for SSL connection)
# COPY src/main/resources/ssl/tidb-truststore.jks tidb-truststore.jks

# # Run the app with truststore
# ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=tidb-truststore.jks", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "app.jar"]

# ----------- STAGE 1: Build with Maven ----------- #
FROM maven:3.9.4-eclipse-temurin-17 as builder

WORKDIR /app

# Copy project files
COPY pom.xml .
COPY src ./src

# Build the app (skip tests for speed)
RUN mvn clean package -DskipTests

# ----------- STAGE 2: Run the built JAR ----------- #
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

COPY src/main/resources/ssl/tidb-truststore.jks tidb-truststore.jks

EXPOSE 8080

ENTRYPOINT ["java", "-Djavax.net.ssl.trustStore=tidb-truststore.jks", "-Djavax.net.ssl.trustStorePassword=changeit", "-jar", "app.jar"]