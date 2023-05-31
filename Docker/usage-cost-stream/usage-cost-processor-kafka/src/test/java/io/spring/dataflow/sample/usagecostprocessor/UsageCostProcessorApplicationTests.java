package io.spring.dataflow.sample.usagecostprocessor;


import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;


import static org.assertj.core.api.Assertions.assertThat;

public class UsageCostProcessorApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void testUsageCostProcessor() {
		try (ConfigurableApplicationContext context = new SpringApplicationBuilder(
				TestChannelBinderConfiguration.getCompleteConfiguration(
						UsageCostProcessorApplication.class)).web(WebApplicationType.NONE)
				.run()) {
			// The reason why we need InputDestination is that we need to send a message to the input channel of the processor.	
			InputDestination source = context.getBean(InputDestination.class);

			UsageDetail usageDetail = new UsageDetail();
			usageDetail.setUserId("user1");
			usageDetail.setDuration(30L);
			usageDetail.setData(100L);

			MessageConverter converter = context.getBean(CompositeMessageConverter.class);
			Map<String, Object> headers = new HashMap<>();
			// put method is used to add a new key-value pair to the map
			// In the given code snippet, the Map<String, Object> headers is used to specify additional message headers for the input message being sent to the processor. 
			// Specifically, it sets the "contentType" header to "application/json".
			// In this case, setting the "contentType" header to "application/json" indicates that the message payload is in JSON format. 
			headers.put("contentType", "application/json");

			MessageHeaders messageHeaders = new MessageHeaders(headers);
			Message<?> message = converter.toMessage(usageDetail, messageHeaders);
			// Print -> message: GenericMessage [payload=byte[43], headers={contentType=application/json, id=2de10cf2-c62e-f383-779d-55e101f3ac06, timestamp=1685572704374}]
			source.send(message);

			OutputDestination target = context.getBean(OutputDestination.class);
			Message<byte[]> sourceMessage = target.receive(10000, "usage-cost");

			UsageCostDetail usageCostDetail = (UsageCostDetail) converter
					.fromMessage(sourceMessage, UsageCostDetail.class);

			assertThat(usageCostDetail.getCallCost()).isEqualTo(3.0);
			assertThat(usageCostDetail.getDataCost()).isEqualTo(5.0);
		}
	}
}