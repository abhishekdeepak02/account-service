
# Define dependencies - Use an official OpenJDK runtime as a parent image
FROM eclipse-temurin:17-jdk-jammy

# Information about the maintainer of this image
LABEL maintainer="lazybytes.com"
# Copy the application JAR file from target folder to the docker image
COPY target/account-0.0.1-SNAPSHOT.jar account-0.0.1-SNAPSHOT.jar
# Execute the application
ENTRYPOINT ["java","-jar","account-0.0.1-SNAPSHOT.jar"]
