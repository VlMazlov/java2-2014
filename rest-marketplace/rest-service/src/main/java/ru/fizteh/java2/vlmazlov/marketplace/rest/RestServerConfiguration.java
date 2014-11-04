package ru.fizteh.java2.vlmazlov.marketplace.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;
import ru.fizteh.java2.vlmazlov.marketplace.model.Ware;
import ru.fizteh.java2.vlmazlov.marketplace.model.WareDescription;

/**
 * Created by vlmazlov on 03.11.14.
 */
@Configuration
public class RestServerConfiguration
{
    @Value("${ru.fizteh.java2.vlmazlov.marketplace.rest.port:1500}")
    private int port;

    @Bean
    public EmbeddedServletContainerFactory servletContainer()
    {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port);
        return factory;
    }

    @Bean
    @RequestMapping(value = "/wares")
    public GenericRestController<WareDescription, Ware> wareRestController()
    {
        return new GenericRestController<>();
    }

    @Bean
    @RequestMapping(value = "/traders")
    public GenericRestController<TraderDescription, Trader> traderRestController()
    {
        return new GenericRestController<>();
    }
}
