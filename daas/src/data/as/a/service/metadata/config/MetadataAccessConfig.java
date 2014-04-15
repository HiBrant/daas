package data.as.a.service.metadata.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "data.as.a.service.access.repo.jpa.sys")
@EnableTransactionManagement
@ComponentScan(basePackages = "data.as.a.service.metadata.transaction")
public class MetadataAccessConfig {

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setPackagesToScan("data.as.a.service.access.entity.jpa.sys");
		
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setGenerateDdl(true);
		adapter.setShowSql(true);
		factory.setJpaVendorAdapter(adapter);
		
		Map<String, String> jpaProperties = new HashMap<String, String>();
		jpaProperties.put("hibernate.connection.driver_class", "com.mysql.jdbc.Driver");
		jpaProperties.put("hibernate.connection.url", "jdbc:mysql://localhost:3306/daas_sys");
		jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProperties.put("hibernate.connection.username", "root");
		jpaProperties.put("hibernate.connection.password", "root");
		factory.setJpaPropertyMap(jpaProperties);
		
		return factory;
	}
	
	@Bean
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager tx = 
			new JpaTransactionManager(entityManagerFactory().getObject());
		
		return tx;
	}
}
