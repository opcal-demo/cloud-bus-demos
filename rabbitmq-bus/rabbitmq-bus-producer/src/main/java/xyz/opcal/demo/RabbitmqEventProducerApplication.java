package xyz.opcal.demo;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Import;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import xyz.opcal.demo.configuration.EventsConfiguration;
import xyz.opcal.demo.event.TestEvent;

@Import(EventsConfiguration.class)
@SpringBootApplication
@RestController
public class RabbitmqEventProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqEventProducerApplication.class, args);
	}

	private @Autowired BusProperties busProperties;
	private @Autowired ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void run(String... args) throws Exception {
		publishEvent();
	}

	@PostMapping("/event")
	public TestEvent publishEvent() {
		var testEvent = new TestEvent(this, busProperties.getId());
		testEvent.setId(UUID.randomUUID().toString());
		applicationEventPublisher.publishEvent(testEvent);
		return testEvent;
	}

}
