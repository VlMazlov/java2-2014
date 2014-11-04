package ru.fizteh.java2.vlmazlov.marketplace.remote;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Configuration
public class RestClientConfiguration
{
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
