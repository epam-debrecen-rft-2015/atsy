package com.epam.rft.atsy.persistence.repositories;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.mock.env.MockPropertySource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;

public class RepositoryITApplciationContextInitializer
    implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    servletContextInitParams(configurableApplicationContext.getEnvironment());
    try {
      dataSource(configurableApplicationContext);
    } catch (NamingException e) {
      throw new IllegalStateException(e);
    }
  }

  private void servletContextInitParams(ConfigurableEnvironment env) {
    MutablePropertySources propertySources = env.getPropertySources();
    MockPropertySource propertySource = new MockPropertySource("servletContextInitParams");
    propertySources.addLast(propertySource);
  }

  private void dataSource(ConfigurableApplicationContext configurableApplicationContext)
      throws NamingException {
    MysqlDataSource dataSource = new MysqlDataSource();
    dataSource.setUrl("jdbc:mysql://localhost:3306/atsy?useUnicode=true&characterEncoding=utf8");
    dataSource.setUser("travis");
    dataSource.setPassword("");

    SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
    builder.bind("jdbc/database", dataSource);
    builder.activate();
  }
}
