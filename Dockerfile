# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the project's JAR file into the container
COPY target/banking-users-service-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8081

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]