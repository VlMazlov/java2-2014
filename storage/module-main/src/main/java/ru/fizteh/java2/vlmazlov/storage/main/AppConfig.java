package ru.fizteh.java2.vlmazlov.storage.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.fizteh.java2.vlmazlov.storage.api.Storeable;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTable;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTableProvider;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTableProviderFactory;
import ru.fizteh.java2.vlmazlov.storage.presentation.DataBasePresenter;

import java.io.IOException;

@Configuration
@EnableAutoConfiguration
@ComponentScan("ru.fizteh.java2.vlmazlov.storage")
@PropertySources({
        @PropertySource("classpath:db_dir.properties"),
        @PropertySource(value = "file:local_db_dir.properties", ignoreResourceNotFound = true)
})

public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholder() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${ru.fizteh.java2.vlmazlov.storage.db_dir}")
    private String dataBaseDirectory;

    @Bean(name = "tableProvider")
    public StoreableTableProvider storeableTableProvider() throws IOException {
        return new StoreableTableProviderFactory().create(dataBaseDirectory);
    }

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(AppConfig.class, args);

        DataBasePresenter<Storeable, StoreableTable> presenter = context.getBean(DataBasePresenter.class);
        presenter.present(args);
    }
}
