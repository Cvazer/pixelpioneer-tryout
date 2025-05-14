FROM maven:3.9.9-eclipse-temurin-11-alpine
COPY . /app/
EXPOSE 8080
WORKDIR /app/
ENTRYPOINT ["mvn", "spring-boot:run"]