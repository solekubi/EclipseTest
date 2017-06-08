package org.max.config;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.util.StringUtils;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "entityManagerFactoryPrimary", basePackages = { "org.max.dao" })
@AutoConfigureAfter({ DataSourceConfig.class })
public class JpaConfig {

	private static Logger logger = LoggerFactory.getLogger(JpaConfig.class);

	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;

	@Bean(name = "entityManagerPrimary")
	public EntityManager entityManager(EntityManagerFactoryBuilder builder) {
		return entityManagerFactoryPrimary(builder).getObject()
				.createEntityManager();
	}

	@Bean(name = "entityManagerFactoryPrimary")
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryPrimary(
			EntityManagerFactoryBuilder builder) {
		return builder.dataSource(dataSource)
				.properties(getVendorProperties(dataSource))
				.packages("org.max.model")
				.persistenceUnit("primaryPersistenceUnit").build();
	}

	@Autowired
	private JpaProperties jpaProperties;

	private Map<String, String> getVendorProperties(DataSource dataSource) {
		Map<String, String> map = jpaProperties.getHibernateProperties(dataSource);
		String auto = env.getProperty("max.datasource.hibernate.hbm2ddl.auto");
		String dialect = env.getProperty("max.datasource.hibernate.dialect");
		String showSql = env.getProperty("max.datasource.hibernate.show_sql");

		if (!StringUtils.isEmpty(dialect)) {
			map.put("hibernate.dialect", dialect);
			logger.info("max.datasource\t\t hibernate.dialect=" + dialect);
		}
		if (!StringUtils.isEmpty(auto)) {
			map.put("hibernate.hbm2ddl.auto", auto);
			logger.info("max.datasource\t\t hibernate.hbm2ddl.auto=" + auto);
		}
		if (!StringUtils.isEmpty(showSql)) {
			map.put("hibernate.show_sql", showSql);
			logger.info("max.datasource\t\t hibernate.show_sql=" + showSql);
		}
		return map;
	}
}
