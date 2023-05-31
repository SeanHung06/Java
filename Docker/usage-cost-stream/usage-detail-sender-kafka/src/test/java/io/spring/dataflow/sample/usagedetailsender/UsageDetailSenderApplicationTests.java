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
    /*  It serves as a simple test case to verify that the application context loads successfully. 
    When executed, this test method will pass if the application context can be created without any errors. 
     It acts as a basic "sanity check" for the application configuration.
    */
    public void contextLoads() {
    }

    @Test
    // This method is also annotated with @Test and contains the actual test logic. 
    // It simulates the sending and receiving of a UsageDetail message using Spring Cloud Stream's test support.
    public void testUsageDetailSender() {
        /*  create a Spring application context with the TestChannelBinderConfiguration 
        use try-with-resources to ensure that the context is closed after the test
        a Spring application context is created with the TestChannelBinderConfiguration to mimic the behavior of the binder in a test environment.
        ConfigurableApplicationContext is an interface in Spring Framework that represents an application context whose configuration can be modified or customized programmatically
        SpringApplicationBuilder is used to create a new application context based on the configuration of UsageDetailSenderApplication class and the TestChannelBinderConfiguration. 
        The web(WebApplicationType.NONE) configuration ensures that the application context is created without any web-specific components.
        */
         try(ConfigurableApplicationContext context = new SpringApplicationBuilder(TestChannelBinderConfiguration.getCompleteConfiguration(UsageDetailSenderApplication.class)).web(WebApplicationType.NONE).run()) {
            //  It represents a destination where messages can be sent or published for testing purposes.
            OutputDestination target = context.getBean(OutputDestination.class);
            // the usage-detail is set in the application.properties file 
            Message<byte[]> sourceMessage = target.receive(10000,"usage-detail");
            // Convert the source message to a UsageDetail object using the CompositeMessageConverter bean
            // It is responsible for converting messages between their in-memory representation (such as byte[]) and higher-level objects.
            MessageConverter converter = context.getBean(CompositeMessageConverter.class);
            UsageDetail usageDetail = (UsageDetail) converter.fromMessage(sourceMessage, UsageDetail.class);

            assertThat(usageDetail.getUserId()).isBetween("user1", "user5");
            assertThat(usageDetail.getDuration()).isBetween(0L, 300L);
            assertThat(usageDetail.getData()).isBetween(0L, 700L);


        }

    }
}
