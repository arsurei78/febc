package net.telos.config;

import net.telos.cmmn.constant.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
		basePackages = {"net.telos.web.repository.first"},
		entityManagerFactoryRef = "firstEntityManager",
		transactionManagerRef = "firstTransactionManager"

)
public class EgovConfigDatasource {

	private AppConstants appConstants;

	public EgovConfigDatasource(@Autowired AppConstants constants) {
		this.appConstants = constants;
	}

	@Bean
	@Primary
	public LocalContainerEntityManagerFactoryBean firstEntityManager() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(this.firstDataSource());
		em.setPackagesToScan(new String[] {
				"net.telos.web.repository.first.entity"
		});
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);

		Map<String, Object> properties = new HashMap<>();
		// 자동 DDL여부
		properties.put("hibernate.hbm2ddl.auto", appConstants.getFirstDdlAuto());
		// JPA dialect설정 정보
		properties.put("hibernate.dialect", appConstants.getFirstPlatform());
		// 테이블 물리 명칭 전략
		properties.put("hibernate.physical_naming_strategy", appConstants.getFirstStrategy());
		// SQL포멧 정리 여부
		properties.put("hibernate.format_sql", true);
		// SQL표시 여부
		properties.put("hibernate.show_sql", true);
		// 강조표시
		properties.put("hibernate.highlight_sql", true);
		em.setJpaPropertyMap(properties);
		return em;
	}

	@Primary
	@Bean(name="dataSource")
	@ConfigurationProperties(prefix="spring.datasource.first")
	public DataSource firstDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Primary
	@Bean(name = "firstTransactionManager")
	public PlatformTransactionManager firstTransactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(firstEntityManager().getObject());
		return transactionManager;
	}

	@Bean(name = "firstJdbcTemplate")
	@Autowired
	public JdbcTemplate firstJdbcTemplate(@Qualifier("dataSource") DataSource firstDatasource) {
		return new JdbcTemplate(firstDatasource);
	}
}
