// BookDBConfig.java
package com.courier.overc360.api.idmaster.config.DBConfig; // Ensure the correct package

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(entityManagerFactoryRef = "replicaEntityManagerFactory", transactionManagerRef = "replicaTransactionManager", basePackages = {
		"com.courier.overc360.api.idmaster.replica.repository" }) // Adjust package name accordingly
public class ReplicaDBConfig {

	@Bean(name = "replicaDataSource")
	@ConfigurationProperties(prefix = "spring.replica.datasource")
	public DataSource dataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name = "replicaEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean replicaEntityManagerFactory(EntityManagerFactoryBuilder builder,
																		   @Qualifier("replicaDataSource") DataSource dataSource) {
		HashMap<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", "update");
		properties.put("hibernate.dialect", "org.hibernate.dialect.SQLServerDialect");
		return builder.dataSource(dataSource).properties(properties)
				.packages("com.courier.overc360.api.idmaster.replica.model")
				.persistenceUnit("Replica").build();
	}

	@Bean(name = "replicaTransactionManager")
	public PlatformTransactionManager replicaTransactionManager(
			@Qualifier("replicaEntityManagerFactory") EntityManagerFactory replicaEntityManagerFactory) {
		return new JpaTransactionManager(replicaEntityManagerFactory);
	}
}
