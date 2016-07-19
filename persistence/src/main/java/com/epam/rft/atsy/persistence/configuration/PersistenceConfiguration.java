package com.epam.rft.atsy.persistence.configuration;

import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.epam.rft.atsy.persistence.repositories")
@ComponentScan("com.epam.rft.atsy.persistence")
public class PersistenceConfiguration {

  private static final String JNDI_DATA_SOURCE = "jdbc/database";


  @Bean(initMethod = "migrate")
  public Flyway flyway(Environment env) {
    Flyway flyway = new Flyway();
    flyway.setBaselineOnMigrate(true);
    flyway.setDataSource(dataSource());
    flyway.setLocations("classpath:db/migration/schema",
        "classpath:db/migration/data/" + env.getActiveProfiles()[0]);
    return flyway;
  }

  @Bean
  @DependsOn("flyway")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
    LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
    em.setDataSource(dataSource);
    em.setPackagesToScan(new String[]{"com.epam.rft.atsy.persistence"});
    JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
    em.setJpaVendorAdapter(vendorAdapter);
    em.setJpaProperties(additionalProperties());
    return em;
  }

  @Bean
  public DataSource dataSource() {
    final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
    dsLookup.setResourceRef(true);
    return dsLookup.getDataSource(JNDI_DATA_SOURCE);
  }

  @Bean(name = "transactionManager")
  public PlatformTransactionManager txManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean
  public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
    return new PersistenceExceptionTranslationPostProcessor();
  }

  private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
    return properties;
  }
}
