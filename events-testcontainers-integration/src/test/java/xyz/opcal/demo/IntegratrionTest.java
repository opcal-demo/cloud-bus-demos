package xyz.opcal.demo;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.shaded.com.google.common.collect.ImmutableSet;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class IntegratrionTest {

	public static final String TAG = String.valueOf(System.currentTimeMillis());
	public static final String ROOT_PATH = System.getenv("PROJECT_DIR");
	public static final String TC_RABBITMQ_USER = "tc";
	public static final String TC_RABBITMQ_PASSWORD = "tc";
	public static final String TC_RABBITMQ_VHOST = "/tc";

	static Map<String, String> tcProfile = new HashMap<>();

	static RabbitMQContainer rabbitMQContainer = new RabbitMQContainer(DockerImageName.parse("rabbitmq:management")) //
			.withVhost(TC_RABBITMQ_VHOST) //
			.withUser(TC_RABBITMQ_USER, TC_RABBITMQ_PASSWORD, ImmutableSet.of("administrator")) //
			.withPermission(TC_RABBITMQ_VHOST, TC_RABBITMQ_USER, ".*", ".*", ".*");

	static GenericContainer<?> producer = new GenericContainer<>(new ImageFromDockerfile("events-producer:" + TAG, true) //
			.withFileFromFile("/tmp/artifact/events-producer.jar", new File("/tmp/artifact/events-producer.jar")) //
			.withFileFromFile("Dockerfile", Paths.get(ROOT_PATH, "events-producer/Dockerfile").toFile())//
	).withExposedPorts(9090);

	static GenericContainer<?> consumer = new GenericContainer<>(new ImageFromDockerfile("events-consumer:" + TAG, true) //
			.withFileFromFile("/tmp/artifact/events-consumer.jar", new File("/tmp/artifact/events-consumer.jar")) //
			.withFileFromFile("Dockerfile", Paths.get(ROOT_PATH, "events-consumer/Dockerfile").toFile())//
	).withExposedPorts(8080);

	static String produceUrl;
	static String consumeUrl;

	@DynamicPropertySource
	static void dynamicProperties(DynamicPropertyRegistry registry) {
		Startables.deepStart(rabbitMQContainer).join();

		tcProfile.put("SPRING_PROFILES_ACTIVE", "tc");
		tcProfile.put("TC_RABBITMQ_ADDRESSES", rabbitMQContainer.getAmqpUrl());
		tcProfile.put("TC_RABBITMQ_USER", TC_RABBITMQ_USER);
		tcProfile.put("TC_RABBITMQ_PASSWORD", TC_RABBITMQ_PASSWORD);
		tcProfile.put("TC_RABBITMQ_VHOST", TC_RABBITMQ_VHOST);

		producer.withEnv(tcProfile);
		consumer.withEnv(tcProfile);

		Startables.deepStart(producer, consumer).join();

		produceUrl = "http://" + producer.getHost() + ":" + producer.getFirstMappedPort() + "/event";
		consumeUrl = "http://" + consumer.getHost() + ":" + consumer.getFirstMappedPort() + "/consume/status";
	}

	@Autowired
	TestRestTemplate restTemplate;

	@Test
	void test() {
		var response = restTemplate.postForEntity(produceUrl, null, String.class);
		System.out.println(response.getBody());
		var consumeStatus = restTemplate.getForObject(consumeUrl, Boolean.class);
		assertTrue(consumeStatus);
	}

}
