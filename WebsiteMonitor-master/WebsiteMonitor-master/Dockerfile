FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . /app

RUN apt-get update && apt-get install -y maven

RUN ./mvnw package -DskipTests || mvn package -DskipTests

ENTRYPOINT ["sh", "-c", "java -cp target/WebsiteMonitor-master-1.0-SNAPSHOT.jar de.frauas.WebsiteMonitor $0", "--"]