package xyz.opcal.demo;

import java.util.concurrent.atomic.AtomicBoolean;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import xyz.opcal.demo.configuration.EventsConfiguration;
import xyz.opcal.demo.event.TestEvent;

@Import(EventsConfiguration.class)
@SpringBootApplication
@RestController
public class KafkaEventConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaEventConsumerApplication.class, args);
	}

	private ObjectMapper objectMapper = new ObjectMapper();
	private AtomicBoolean hasConsume = new AtomicBoolean(false);

	@SneakyThrows
	@EventListener
	public void consumer(TestEvent testEvent) {
		if (!hasConsume.get()) {
			hasConsume.set(true);
		}
		System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(testEvent));
	}

	@GetMapping("/consume/status")
	public boolean hasConsume() {
		return hasConsume.get();
	}

}
