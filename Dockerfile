# --- ESTÁGIO 1: BUILD ---
# Usamos uma imagem do Maven com a JDK 21
FROM maven:3.9-eclipse-temurin-21 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia o código-fonte
COPY . .

# Roda o build do Quarkus (pulando testes, crucial para o deploy)
RUN mvn package -Dquarkus.package.type=fast-jar -DskipTests


# --- ESTÁO 2: RUN ---
# Usamos a imagem base que você já tinha
FROM registry.access.redhat.com/ubi9/openjdk-21:1.23

# Define o usuário (boa prática)
USER 185

# O "pulo do gato": Copia os arquivos do estágio "build"
COPY --from=build /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build /app/target/quarkus-app/app/ /deployments/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /deployments/quarkus/
COPY --from=build /app/target/quarkus-app/*.jar /deployments/

# Expõe a porta
EXPOSE 8080

# Comando para iniciar a aplicação
CMD ["java", "-jar", "/deployments/quarkus-run.jar"]