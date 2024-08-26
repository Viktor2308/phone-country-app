FROM amazoncorretto:17-alpine
WORKDIR /app
COPY target/*.jar /app.jar
EXPOSE 8088
ENTRYPOINT ["java", "-jar", "/app.jar"]