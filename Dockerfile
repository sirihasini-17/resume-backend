# ==========================================
# Stage 1: Build the Application
# ==========================================
FROM maven:3.9.5-eclipse-temurin-17 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and package
COPY src ./src
RUN mvn clean package -DskipTests

# ==========================================
# Stage 2: Create the minimal Runtime Image
# ==========================================
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Expose the standard Spring Boot port
EXPOSE 8080

# Copy the built jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Run the application with memory limits to prevent Render OOM/hanging
ENTRYPOINT ["java", "-Xmx300m", "-Xss512k", "-XX:MaxMetaspaceSize=128m", "-jar", "app.jar"]
