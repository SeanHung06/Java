package io.spring.dataflow.sample.usagedetailsender;

import org.junit.jupiter.api.Test;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.converter.CompositeMessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;


public class UsageDetailSenderApplicationTests {
    
    @Test
    // verify that the application context loads successfully
    public void contextLoads() {
    }

    @Test
    public void testUsageDetailSender() {
        try(ConfigurableApplicationContext context = new SpringApplicationBuilder(TestChannelBinderConfiguration.getCompleteConfiguration(UsageDetailSenderApplication.class)).web(WebApplicationType.NONE).run()) {
            OutputDestination target = context.getBean(OutputDestination.class);
            Message<byte[]> sourceMessage = target.receive(10000,"usage-detail");
        
            MessageConverter converter = context.getBean(CompositeMessageConverter.class);
            UsageDetail usageDetail = (UsageDetail) converter.fromMessage(sourceMessage, UsageDetail.class);

            assertThat(usageDetail.getUserId()).isBetween("user1", "user5");
            assertThat(usageDetail.getDuration()).isBetween(0L, 300L);
            assertThat(usageDetail.getData()).isBetween(0L, 700L);


        }

    }
}
