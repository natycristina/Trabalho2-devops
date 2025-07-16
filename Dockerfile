# Etapa 1: build da aplicação usando Maven + JDK 17
FROM maven:3.8.4-openjdk-17-slim AS build-stage

WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: imagem final só com o jar (JRE 17)
FROM openjdk:17-jdk-slim AS production-stage

WORKDIR /app
ENV TZ=America/Sao_Paulo
# Adiciona curl
RUN apt update && apt install curl -y

COPY --from=build-stage /app/target/*.jar app.jar
CMD ["java", "-jar", "app.jar"]
