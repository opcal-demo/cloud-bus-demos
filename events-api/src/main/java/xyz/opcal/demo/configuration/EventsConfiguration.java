package xyz.opcal.demo.configuration;

import org.springframework.cloud.bus.jackson.RemoteApplicationEventScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RemoteApplicationEventScan(basePackages = { "xyz.opcal.demo" })
public class EventsConfiguration {

}
