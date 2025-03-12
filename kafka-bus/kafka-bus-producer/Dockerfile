FROM ghcr.io/opcal/eclipse-temurin:17-jre

WORKDIR /app

COPY ./target/kafka-bus-producer.jar /app

EXPOSE 9090

CMD ["java", "-jar", "/app/kafka-bus-producer.jar"]
  