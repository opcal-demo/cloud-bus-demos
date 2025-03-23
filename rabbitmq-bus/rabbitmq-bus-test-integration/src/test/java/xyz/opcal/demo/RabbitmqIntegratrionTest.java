package xyz.opcal.demo;


import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class RabbitmqIntegratrionTest {

	static String produceUrl = "http://127.0.0.1:9090/event";
	static String consumeUrl = "http://127.0.0.1:8080/consume/status";

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void test() throws InterruptedException {
		Thread.sleep(5000);
		var response = restTemplate.postForEntity(produceUrl, null, String.class);
		System.out.println(response.getBody());
		
		Thread.sleep(5000);
		var consumeStatus = restTemplate.getForObject(consumeUrl, Boolean.class);
		assertTrue(consumeStatus);
	}

}
