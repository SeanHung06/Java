package io.spring.dataflow.sample.usagecostlogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import java.util.function.Consumer;

@Configuration
public class UsageCostLogger {
    private static final Logger log = LoggerFactory.getLogger(UsageCostLogger.class);
    // declare a Consumer bean that accepts a UsageCostDetail
    // Spring Cloud Stream will discover this function and bind its input to the input destination configured for the messaging middleware
    @Bean 
    public Consumer<UsageCostDetail> log(){
        return usageCostDetail -> {
            log.info(usageCostDetail.toString());
        };
    }

}
