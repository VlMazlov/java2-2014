package ru.fizteh.java2.vlmazlov.marketplace;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.fizteh.java2.vlmazlov.marketplace")
@PropertySource(value = "file:marketplace.properties", ignoreResourceNotFound = true)
public class BackendConfiguration
{
    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args)
    {
        SpringApplication.run(BackendConfiguration.class, args);
    }
}
