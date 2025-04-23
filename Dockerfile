# Java ve Maven içeren imaj kullan
FROM maven:3.9.6-eclipse-temurin-17 AS build

# Proje dosyalarını konteynıra kopyala
COPY . /app
WORKDIR /app

# Maven ile build (jar dosyasını üret)
RUN mvn clean package -DskipTests

# ------------------

FROM eclipse-temurin:17-jdk-alpine

# Build'ten oluşan jar'ı al
COPY --from=build /app/target/*.jar app.jar

# Jar dosyasını çalıştır
ENTRYPOINT ["java", "-jar", "/app.jar"]
