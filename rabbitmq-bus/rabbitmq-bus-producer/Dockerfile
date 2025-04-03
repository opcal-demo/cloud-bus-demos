FROM ghcr.io/opcal/eclipse-temurin:21-jre

WORKDIR /app

COPY ./target/rabbitmq-bus-producer.jar /app

EXPOSE 9090

CMD ["java", "-jar", "/app/rabbitmq-bus-producer.jar"]
  