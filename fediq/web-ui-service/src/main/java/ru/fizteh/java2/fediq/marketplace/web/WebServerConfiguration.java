package ru.fizteh.java2.fediq.marketplace.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:me@fediq.ru"/>
 * @since 13/10/14
 */
@Configuration
@EnableWebMvc
public class WebServerConfiguration {
    @Value("${ru.fizteh.java2.fediq.marketplace.web.port:5432}")
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

    // TODO REST Client
}
