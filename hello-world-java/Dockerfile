# Usa a imagem do Maven para compilar o projeto
FROM maven:3.9.5-eclipse-temurin-17 as builder
WORKDIR /app

# Copia os arquivos do projeto
COPY pom.xml .
COPY src ./src

# Compila a aplicação e empacota o JAR
RUN mvn clean package

# Usa uma imagem menor para rodar a aplicação
FROM eclipse-temurin:17-jre
WORKDIR /app

# Expõe a porta 8080 para acesso externo
EXPOSE 8080

# Copia o JAR gerado do estágio anterior
COPY --from=builder /app/target/*.jar app.jar

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
