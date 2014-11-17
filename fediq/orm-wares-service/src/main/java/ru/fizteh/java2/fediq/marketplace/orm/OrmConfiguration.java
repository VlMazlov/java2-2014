package ru.fizteh.java2.fediq.marketplace.orm;

import com.jolbox.bonecp.BoneCPDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate3.HibernateTransactionManager;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.fizteh.java2.fediq.marketplace.orm.model.MeasuringEntity;
import ru.fizteh.java2.fediq.marketplace.orm.model.WareEntity;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 10.11.14
 */
@Configuration
@EnableTransactionManagement
public class OrmConfiguration {

    @Value("${ru.fizteh.java2.fediq.marketplace.orm.driver:com.mysql.jdbc.Driver}")
    private String jdbcDriver;
    @Value("${ru.fizteh.java2.fediq.marketplace.orm.url:jdbc:mysql://localhost:3306/wares}")
    private String jdbcUrl;
    @Value("${ru.fizteh.java2.fediq.marketplace.orm.username}")
    private String jdbcUsername;
    @Value("${ru.fizteh.java2.fediq.marketplace.orm.password}")
    private String jdbcPassword;

    @Value("${ru.fizteh.java2.fediq.marketplace.orm.dialect:org.hibernate.dialect.MySQLDialect}")
    private String hibernateDialect;
    @Value("${ru.fizteh.java2.fediq.marketplace.orm.hbm2ddl:update}")
    private String hbm2ddl;


    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        BoneCPDataSource dataSource = new BoneCPDataSource();
        dataSource.setDriverClass(jdbcDriver);
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setUsername(jdbcUsername);
        dataSource.setPassword(jdbcPassword);
        return dataSource;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) throws Exception {
        return new HibernateTransactionManager(sessionFactory);
    }

    @Bean
    @Autowired
    public FactoryBean<SessionFactory> sessionFactory(DataSource dataSource) {
        AnnotationSessionFactoryBean factoryBean = new AnnotationSessionFactoryBean();

        factoryBean.setDataSource(dataSource);

        factoryBean.setAnnotatedClasses(WareEntity.class, MeasuringEntity.class);

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", hibernateDialect);
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", hbm2ddl);

        factoryBean.setHibernateProperties(hibernateProperties);
        return factoryBean;
    }
}
