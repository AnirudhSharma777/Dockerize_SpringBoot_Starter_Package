
# Dockerized Spring Boot Starter Pack with PostgreSQL

This project is a basic template to get started with a Dockerized Spring Boot application that connects to a PostgreSQL database.

## Prerequisites

Before you begin, ensure you have the following installed on your machine:

- **Java 17** (or any supported version)
- **Maven** (for building the Spring Boot app)
- **Docker** and **Docker Compose**

## Project Structure

```
├── Dockerfile
├── docker-compose.yml
├── src
│   ├── main
│   │   ├── java
│   │   └── resources
├── pom.xml
└── README.md
```

- **Dockerfile**: Defines how the Spring Boot app is packaged into a Docker container.
- **docker-compose.yml**: Defines and orchestrates the Spring Boot application and PostgreSQL database services.
- **src**: The source code of your Spring Boot application.
- **pom.xml**: Maven configuration file for managing dependencies and build processes.

## How to Build and Run the Project

### Step 1: Build the Spring Boot Application

First, package your Spring Boot application into a JAR file by running the following Maven command:

```bash
mvn clean package -DskipTests
```

This will generate a JAR file in the `target/` directory.

### Step 2: Run Docker Compose

To run the Spring Boot application and PostgreSQL using Docker, use the following command:

```bash
docker-compose up --build
```

This will:
- Build the Docker image for the Spring Boot application using the `Dockerfile`.
- Start up the Spring Boot app and PostgreSQL database services defined in `docker-compose.yml`.

### Step 3: Access the Application

Once the containers are running, the Spring Boot application will be accessible at:

```
http://localhost:8080
```

### Step 4: Stop the Containers

To stop the running containers, use:

```bash
docker-compose down
```

This will stop and remove all the containers, networks, and volumes defined in the `docker-compose.yml`.

## Configuration

### Dockerfile

The `Dockerfile` is used to build the Docker image for the Spring Boot app. Here's a sample `Dockerfile`:

```dockerfile
# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the jar file into the container at /app
COPY target/your-app-name.jar app.jar

# Expose port 8080 to the outside world
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
```

Make sure to replace `your-app-name.jar` with the actual JAR file name generated in the `target` directory after running `mvn clean package`.

### docker-compose.yml

The `docker-compose.yml` file orchestrates the Spring Boot app and PostgreSQL services.

```yaml
version: '3.8'

services:
  java_app:
    image: your-app-name
    build:
      context: .
      dockerfile: Dockerfile
    ports:
      - "8080:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:postgresql://java_db:5432/postgresdb
      SPRING_DATASOURCE_USERNAME: postgres
      SPRING_DATASOURCE_PASSWORD: postgres
    depends_on:
      - java_db

  java_db:
    image: postgres:latest
    environment:
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
      POSTGRES_DB: postgresdb
    ports:
      - "5432:5432"
    volumes:
      - pgdata:/var/lib/postgresql/data

volumes:
  pgdata:
```

### Application Properties for PostgreSQL

In your `application.properties` or `application.yml` file, make sure to configure the database connection like this:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

These environment variables are passed in the `docker-compose.yml` file to ensure PostgreSQL connectivity.

## How to Push Docker Image to Docker Hub (Optional)

1. Tag your Docker image:
   ```bash
   docker tag your-app-name your-dockerhub-username/your-repository-name:tag
   ```

2. Push the image to Docker Hub:
   ```bash
   docker push your-dockerhub-username/your-repository-name:tag
   ```

## Troubleshooting

- **Port Conflict**: If port `8080` or `5432` is already in use, stop any running services using those ports or change the ports in `docker-compose.yml`.
- **Database Connection Issues**: Make sure the PostgreSQL service is running and the database credentials in the `docker-compose.yml` and `application.properties` are correct.

## License

This project is licensed under the MIT License.
