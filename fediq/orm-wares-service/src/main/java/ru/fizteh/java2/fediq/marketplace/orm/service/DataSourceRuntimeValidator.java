package ru.fizteh.java2.fediq.marketplace.orm.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

/**
 * @author Fedor S. Lavrentyev <a href="mailto:fediq@oorraa.net"/>
 * @since 11.11.14
 */
@Service
public class DataSourceRuntimeValidator {
    private static final Logger log = LoggerFactory.getLogger(DataSourceRuntimeValidator.class);
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    public void validate() {
        if (new JdbcTemplate(dataSource).queryForObject("select 1=1", Integer.class) != 1) {
            throw new IllegalStateException("Failed to connect to SQL Server");
        }
        log.info("SQL connection is valid");
    }
}
