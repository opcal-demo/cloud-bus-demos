package xyz.opcal.demo;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cloud.bus.BusProperties;
import org.springframework.context.ApplicationEventPublisher;

import xyz.opcal.demo.event.TestEvent;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
@TestInstance(Lifecycle.PER_CLASS)
class RabbitmqEventConsumerApplicationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	BusProperties busProperties;
	
	@Autowired
	ApplicationEventPublisher applicationEventPublisher;
	
	@BeforeAll
	void setup() {
		var testEvent = new TestEvent(this, busProperties.getId());
		testEvent.setId(UUID.randomUUID().toString());
		applicationEventPublisher.publishEvent(testEvent);
	}

	@Test
	void test() {
		var consumeStatus = restTemplate.getForObject("/consume/status", Boolean.class);
		System.out.println(consumeStatus);
		assertTrue(consumeStatus);
	}

}
