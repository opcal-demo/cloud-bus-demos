FROM ghcr.io/opcal/eclipse-temurin:17-jre

WORKDIR /app

COPY ./target/kafka-bus-consumer.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/kafka-bus-consumer.jar"]
  