FROM ghcr.io/opcal/eclipse-temurin:17-jre

WORKDIR /app

COPY /tmp/artifact/events-producer.jar /app

EXPOSE 9090

CMD ["java", "-jar", "/app/events-producer.jar"]
  