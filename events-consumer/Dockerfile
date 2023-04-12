FROM ghcr.io/opcal/eclipse-temurin:17-jre

WORKDIR /app

COPY /tmp/artifact/events-consumer.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/events-consumer.jar"]
  