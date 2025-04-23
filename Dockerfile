# Dockerfile

# Java 17 JDK içeren hafif bir imaj kullanıyoruz
FROM eclipse-temurin:17-jdk-alpine

# Uygulama jar dosyasını app.jar adıyla kopyala
COPY target/*.jar app.jar

# JAR dosyasını çalıştır
ENTRYPOINT ["java", "-jar", "/app.jar"]
