
FROM openjdk:23-jdk
WORKDIR /app
COPY target/*.jar notesManagement.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "notesManagement.jar"]
