FROM openjdk:17-alpine
ENV PORT=9900
WORKDIR /app
COPY target/Bank-1.0-SNAPSHOT.jar .
CMD ["java", "-jar", "Bank-1.0-SNAPSHOT.jar"]