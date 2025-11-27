FROM ghcr.io/opcal/eclipse-temurin:25-jre

WORKDIR /app

COPY ./target/rabbitmq-bus-consumer.jar /app

EXPOSE 8080

CMD ["java", "-jar", "/app/rabbitmq-bus-consumer.jar"]
  