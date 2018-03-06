package com.uas.jdbc.config;

import com.uas.jdbc.data.MyJdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * Created by Pro1 on 2018/3/6.
 */
@Configuration
public class JdbcConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public MyJdbcTemplate jdbcTemplate() {
        return new MyJdbcTemplate(dataSource);
    }

}
