FROM bellsoft/liberica-openjdk-alpine:17

ARG JAR_FILE=build/libs/*.jar

COPY ${JAR_FILE} demo.jar

ENTRYPOINT ["java", "-jar", "demo.jar"]

# ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "demo.jar"]