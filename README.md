My Skills List — Spring Boot + JSP + MySQL (Dockerized)

#######################################################

Summary
My Skills List is a Spring Boot web application that allows users to log in and manage skill data using a clean JSP interface and a MySQL database.

The project demonstrates:
- Spring Boot 3 MVC with JSP views
- JPA with MySQL persistence
- Secure login using Spring Security (in-memory users)
- Full Docker setup for easy build and deployment

#######################################################

Tech Stack
- Java 17
- Spring Boot 3.x
- Spring MVC + JSP + JSTL
- Spring Data JPA
- Spring Security
- MySQL 8
- Docker + Docker Compose

#######################################################

Project Structure

<img width="428" height="332" alt="image" src="https://github.com/user-attachments/assets/5c138ee7-63bb-4df9-9724-c4cd3e7b0348" />

#######################################################

Running with Docker
> docker compose build --no-cache
> docker compose up -d

Access the app
http://localhost:8080/ 

Login credentials (in-memory users): 
Username/Password: lia/pass or leo/pass

Stop and clean up
docker compose down

# To also remove the database volume:
docker compose down -v

#######################################################

Docker Setup Summary

Dockerfile:
- Multi-stage build (Maven → JRE)
- Copies built JAR/WAR into a small runtime image
- Uses eclipse-temurin:17-jre-jammy
- Exposes port 8080

docker-compose.yml
- db: MySQL 8 with UTF-8 configuration
- app: depends on db health, runs Spring Boot
- Shares an internal Docker network
- Exposes:
App → localhost:8080
DB → localhost:3306 (optional)


