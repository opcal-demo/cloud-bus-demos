package xyz.opcal.demo.event;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class TestEvent extends RemoteApplicationEvent {

	private static final long serialVersionUID = -2874556841822505290L;

	private String id;

	public TestEvent() {
		super();
	}

	public TestEvent(Object source, String originService) {
		this(source, originService, null);
	}

	public TestEvent(Object source, String originService, String destination) {
		super(source, originService, DEFAULT_DESTINATION_FACTORY.getDestination(destination));
	}
}
