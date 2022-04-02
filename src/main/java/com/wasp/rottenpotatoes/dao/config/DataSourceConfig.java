package com.wasp.rottenpotatoes.dao.config;

import org.flywaydb.core.Flyway;
import org.jasypt.util.text.BasicTextEncryptor;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:application.properties")
public class DataSourceConfig {

    @Value("${PASSWORD_SECRET}")
    private String passwordSecret;

    @Bean(initMethod = "migrate")
    public Flyway flyway(DataSource dataSource) {
        return new Flyway(Flyway.configure().dataSource(dataSource));
    }

    @Bean
    public PGSimpleDataSource pgSimpleDataSource(@Value("${db.url}") String dbUrl,
                                                 @Value("${db.username}") String username,
                                                 @Value("${db.password}") String password) {
        PGSimpleDataSource pgSimpleDataSource = new PGSimpleDataSource();
        pgSimpleDataSource.setUrl(dbUrl);
        pgSimpleDataSource.setUser(username);
        pgSimpleDataSource.setPassword(getDecryptedPassword(password));
        return pgSimpleDataSource;
    }

    private String getDecryptedPassword(String password) {
        BasicTextEncryptor decryptor = new BasicTextEncryptor();
        decryptor.setPasswordCharArray(passwordSecret.toCharArray());
        return decryptor.decrypt(password);
    }
}
