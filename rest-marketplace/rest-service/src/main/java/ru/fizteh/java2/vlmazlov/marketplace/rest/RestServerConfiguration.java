package ru.fizteh.java2.vlmazlov.marketplace.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Configuration
public class RestServerConfiguration
{
    @Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.port:8080}")
    private int port;

    @Bean
    public EmbeddedServletContainerFactory servletContainer()
    {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port);
        return factory;
    }
}
