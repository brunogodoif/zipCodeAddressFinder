# Etapa de build
FROM maven:3.8.5-openjdk-11 AS build
WORKDIR /app
COPY pom.xml ./
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests=true

# Etapa final (imagem mais enxuta)
FROM openjdk:11-jre-slim
WORKDIR /app
# Apenas o JAR final é copiado para a imagem final
COPY --from=build /app/target/zipcode-address-finder-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
