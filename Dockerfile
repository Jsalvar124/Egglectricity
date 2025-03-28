# Use an official OpenJDK 21 runtime as a base image
FROM eclipse-temurin:21-jdk

# Set working directory inside the container
WORKDIR /app

# Copy Maven wrapper and pom.xml (to leverage caching)
COPY .mvn/ .mvn
COPY mvnw pom.xml ./

# Grant execute permission to Maven wrapper
RUN chmod +x mvnw

# Download dependencies before copying source code (improves build speed)
RUN ./mvnw dependency:go-offline

# Copy the actual application source code
COPY src ./src

# Build the application and ensure the final JAR is correctly named
RUN ./mvnw clean package -DskipTests && \
    mv target/*SNAPSHOT.jar target/egglectricity.jar

# Remove the default Spring Boot port
# EXPOSE 8080

# Run the application with environment variables
CMD ["sh", "-c", "java -jar target/egglectricity.jar --server.port=${PORT} --spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME} --spring.datasource.username=${DB_USERNAME} --spring.datasource.password=${DB_PASSWORD}"]
