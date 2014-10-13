package ru.fizteh.java2.fediq.marketplace;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 13/10/14
 */
@Configuration
@EnableAutoConfiguration
@EnableWebMvc
@ComponentScan("ru.fizteh.java2.fediq.marketplace")
@PropertySource(value = "file:marketplace.properties", ignoreResourceNotFound = true)
public class FrontendConfig {

    @Bean
    public PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    public static void main(String[] args) {
        SpringApplication.run(FrontendConfig.class, args);
    }
}
