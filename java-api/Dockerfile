FROM maven:3.6.3-jdk-11-slim AS MAVEN_BUILD
COPY pom.xml /java-api/
COPY src /java-api/src
RUN mvn -f /java-api/pom.xml clean package

FROM adoptopenjdk/openjdk11:jdk-11.0.10_9-alpine
COPY --from=MAVEN_BUILD /java-api/target/java-api-0.0.1-SNAPSHOT.jar /java-api.jar
CMD ["java", "-jar", "/java-api.jar"]
