package ru.fizteh.java2.vlmazlov.marketplace.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;
import ru.fizteh.java2.vlmazlov.marketplace.api.GenericManager;
import ru.fizteh.java2.vlmazlov.marketplace.model.Trader;
import ru.fizteh.java2.vlmazlov.marketplace.model.TraderDescription;
import ru.fizteh.java2.vlmazlov.marketplace.model.Ware;
import ru.fizteh.java2.vlmazlov.marketplace.model.WareDescription;
import ru.fizteh.java2.vlmazlov.marketplace.web.ui.view.GenericMarketPageView;
import ru.fizteh.java2.vlmazlov.marketplace.web.urlmanager.URLManager;

/**
 * Created by vlmazlov on 07.11.14.
 */
@Configuration
@EnableWebMvc
public class WebInterfaceConfiguration extends WebMvcConfigurerAdapter
{
    @Value("${ru.fizteh.java2.vlmazlov.marketplace.web.port:5500}")
    private int port;

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port);
        return factory;
    }

    @Bean
    @Autowired
    public SpringResourceResourceResolver resourceResolver() {
        return new SpringResourceResourceResolver();
    }

    @Bean
    public TemplateResolver defaultTemplateResolver() {
        TemplateResolver templateResolver = new TemplateResolver();
        templateResolver.setPrefix("classpath:/html/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML5");
        templateResolver.setResourceResolver(resourceResolver());
        return templateResolver;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/css/");
    }
}
