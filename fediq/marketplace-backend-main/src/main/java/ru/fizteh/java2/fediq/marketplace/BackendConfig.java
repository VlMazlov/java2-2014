package ru.fizteh.java2.fediq.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 06/10/14
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.fizteh.java2.fediq.marketplace")
@PropertySource(value = "file:marketplace.properties", ignoreResourceNotFound = true)
@PropertySource(value = "file:marketplace-local.properties", ignoreResourceNotFound = true)
public class BackendConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        SpringApplication.run(BackendConfig.class, args);
    }

}
