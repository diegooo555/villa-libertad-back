# Etapa de compilación: Maven 3.9.6 + JDK 17
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder

WORKDIR /usr/src/app
COPY . .

# Compilar y empaquetar la aplicación, omitiendo tests para producción rápida
RUN mvn clean package -DskipTests

# Etapa de ejecución: solo JRE
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app
COPY --from=builder /usr/src/app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]
