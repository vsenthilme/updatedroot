package com.courier.overc360.api.common.config.DBConfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(entityManagerFactoryRef = "primaryManagerFactory", basePackages = {
		"com.courier.overc360.api.idmaster.primary.repository" })
public class PrimaryDBConfig {
	@Primary
	@Bean(name = "dataSource")
	@ConfigurationProperties(prefix = "spring.primary.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "primaryManagerFactory")
	public LocalContainerEntityManagerFactoryBean primaryManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("dataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		return builder.dataSource(dataSource).properties(properties)
				.packages("com.courier.overc360.api.idmaster.primary.model").persistenceUnit("Primary").build();
	}

	@Primary
	@Bean(name = "transactionManager")
	public PlatformTransactionManager transactionManager(
			@Qualifier("primaryManagerFactory") EntityManagerFactory primaryManagerFactory) {
		return new JpaTransactionManager(primaryManagerFactory);
	}
}