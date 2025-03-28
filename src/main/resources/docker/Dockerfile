# Build Stage: Use Mandrel builder image for native compilation
FROM quay.io/quarkus/ubi9-quarkus-mandrel-builder-image:jdk-21 AS build

# Set working directory
WORKDIR /home/quarkus

# Copy project files from the build context (project root)
COPY --chown=quarkus:quarkus pom.xml .
COPY --chown=quarkus:quarkus .mvn .mvn
COPY --chown=quarkus:quarkus mvnw mvnw
COPY --chown=quarkus:quarkus src src

# Ensure the Maven Wrapper has execute permissions
RUN chmod +x mvnw

# Build native Quarkus application
RUN ./mvnw package -Pnative

# Runtime Stage: Use UBI minimal image to run the native binary
FROM registry.access.redhat.com/ubi9/ubi-minimal:9.5

# Copy the built native executable directly to /application (not a directory)
COPY --from=build /home/quarkus/target/*-runner /application

# Ensure the application has execute permissions
RUN chmod +x /application

# Expose application ports
EXPOSE 8080 9000

# Run the application
CMD ["/application", "-Dquarkus.http.host=0.0.0.0"]