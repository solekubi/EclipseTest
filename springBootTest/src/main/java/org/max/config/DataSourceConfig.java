package org.max.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
public class DataSourceConfig {

	
	@Bean(name = "dataSource",destroyMethod = "close")
	@Qualifier("dataSource")
	@ConfigurationProperties(prefix="max.datasource")
	public DataSource dataSource(){
		return DataSourceBuilder.create().build();
	}
}
