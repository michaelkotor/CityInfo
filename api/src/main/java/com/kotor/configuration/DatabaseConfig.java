package com.kotor.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

@Component
public class DatabaseConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public void prepareDatabase() throws SQLException {
        ScriptUtils.executeSqlScript(dataSource.getConnection(),
                new EncodedResource(new ClassPathResource("schema.sql"),
                        StandardCharsets.UTF_8));
//        ScriptUtils.executeSqlScript(dataSource.getConnection(),
//                new EncodedResource(new ClassPathResource("data.sql"),
//                        StandardCharsets.UTF_8));
    }
}
