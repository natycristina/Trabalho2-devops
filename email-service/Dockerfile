# Etapa 1: build da aplicação usando Maven + JDK 17
FROM maven:3.8.4-openjdk-17-slim AS build-stage

WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build da aplicação
RUN mvn clean package -DskipTests

# Etapa 2: imagem final só com o jar (JRE 17)
FROM openjdk:17-jdk-slim AS production-stage

# Instalar curl para health check
RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

WORKDIR /app
ENV TZ=America/Sao_Paulo

# Copiar o JAR da etapa de build
COPY --from=build-stage /app/target/*.jar email-service.jar

# Expor porta 8081
EXPOSE 8081

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8081/api/email/health || exit 1

# Comando para executar a aplicação
CMD ["java", "-jar", "email-service.jar"]