package xyz.opcal.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestRestTemplate
class KafkaEventProducerApplicationTest {

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void test() {
		var response = restTemplate.postForEntity("/event", null, String.class);
		System.out.println(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
