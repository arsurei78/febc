package egovframework.config;

import egovframework.cmmn.constant.ApplicationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
		basePackages = {"egovframework.web.repository.second"},
		entityManagerFactoryRef = "secondEntityManager",
		transactionManagerRef = "secondTransactionManager"
)
public class EgovConfigSecondDatasource {


	@Bean
	public LocalContainerEntityManagerFactoryBean secondEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(this.firstDataSource());
		em.setPackagesToScan(new String[] {
				"egovframework.web.repository.second.entity"
		});
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Map<String, Object> properties = new HashMap<>();
		properties.put("hibernate.hbm2ddl.auto", ApplicationConstants.secondDdlAuto);
		properties.put("hibernate.dialect", ApplicationConstants.secondPlatform);
		properties.put("hibernate.physical_naming_strategy", ApplicationConstants.secondStrategy);
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Bean(name="secondDataSource")
	@ConfigurationProperties(prefix="spring.datasource.second")
	public DataSource firstDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(name="secondTransactionManager")
	public PlatformTransactionManager secondTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(secondEntityManager().getObject());
		return transactionManager;
	}

	@Bean(name = "secondJdbcTemplate")
	@Autowired
	public JdbcTemplate secondJdbcTemplate(@Qualifier("secondDataSource") DataSource secondDatasource) {
		return new JdbcTemplate(secondDatasource);
	}
}
