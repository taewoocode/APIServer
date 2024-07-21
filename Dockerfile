# Use an appropriate base image for Java
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the local machine to the container
COPY build/libs/APIServer-0.0.1-SNAPSHOT.jar app.jar

# Set environment variables
ENV SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/user?serverTimezone=Asia/Seoul
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=1502489awdsa!!@
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=create
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_JPA_PROPERTIES_HIBERNATE_SHOW_SQL=true
ENV SPRING_JPA_PROPERTIES_HIBERNATE_FORMAT_SQL=true
ENV SPRING_JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQLDialect

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]