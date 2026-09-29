# Etapa 1: Construção (Build) usando o Maven
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
# Compila o projeto e gera o arquivo .jar (ignorando testes para ser mais rápido)
RUN mvn clean package -DskipTests

# Etapa 2: Execução (Run) usando uma versão mais leve do Java
FROM eclipse-temurin:17-jre
WORKDIR /app
# Copia o .jar gerado na etapa anterior para dentro do servidor final
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Comando que liga o Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]