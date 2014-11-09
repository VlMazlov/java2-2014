package ru.fizteh.java2.vlmazlov.marketplace.remote;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.fizteh.java2.vlmazlov.marketplace.web.urlmanager.URLManager;

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

    @Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.traders.port:8000}")
    private int tradersRestPort;

    //@Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.wares.port:8001}")
    @Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.traders.port:8000}")
    private int waresRestPort;
}
