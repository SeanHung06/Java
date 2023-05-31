package io.spring.dataflow.sample.usagedetailsender;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.Random;
import java.util.function.Supplier;

/*  
In the provided example, the UsageDetailSender class is annotated with @Configuration, indicating that it serves as a source of bean definitions. 
It defines a single bean named sendEvents() using the @Bean annotation, which returns a Supplier<UsageDetail> object. 
This bean supplies random UsageDetail objects when invoked, with values generated randomly for userId, duration, and data.
Overall, the @Configuration annotation is used to configure the Spring application context and define beans using @Bean methods within the annotated class.
*/


@Configuration
public class UsageDetailSender {
    private String[] users = {"user1", "user2", "user3", "user4", "user5"};
    @Bean
    // Supplier represents a function which does not take in any argument but produces a value of type T.
    public Supplier<UsageDetail> sendEvents() {
        return () -> {
            UsageDetail usageDetail = new UsageDetail();
            usageDetail.setUserId(this.users[new Random().nextInt(5)]);
            usageDetail.setDuration(new Random().nextInt(300));
            usageDetail.setData(new Random().nextInt(700));
            return usageDetail;
        };
    }
}



