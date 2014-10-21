package ru.fizteh.java2.vlmazlov.storage.main;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTableProvider;
import ru.fizteh.java2.vlmazlov.storage.core.StoreableTableProviderFactory;

import java.io.IOException;

/**
 * Created by vlmazlov on 13.10.14.
 */
@Configuration
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
}
