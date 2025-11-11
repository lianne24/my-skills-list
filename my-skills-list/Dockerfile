# ---------- Build stage ----------
FROM maven:3.9.9-eclipse-temurin-17 AS build
WORKDIR /app

# Warm dependency cache (faster rebuilds)
COPY pom.xml .
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests dependency:go-offline

# Build the project
COPY src ./src
RUN --mount=type=cache,target=/root/.m2 mvn -q -DskipTests package \
 && echo "==== contents of target ====" \
 && ls -lah target || true \
 && sh -c 'set -eu; \
      ART="$(ls target/*.jar 2>/dev/null || true)"; \
      [ -z "$ART" ] && ART="$(ls target/*.war 2>/dev/null || true)"; \
      if [ -z "$ART" ]; then \
        echo "No JAR/WAR produced. Check the Maven logs above."; exit 2; \
      fi; \
      cp "$ART" /app/app.jar && echo "Copied $ART -> /app/app.jar"'

# ---------- Runtime stage ----------
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# Optional: non-root user
RUN useradd -ms /bin/bash spring && chown -R spring:spring /app
USER spring

# Copy the normalized artifact
COPY --from=build /app/app.jar /app/app.jar

ENV SERVER_PORT=8080 JAVA_OPTS=""
EXPOSE 8080

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${SERVER_PORT} -jar /app/app.jar"]
