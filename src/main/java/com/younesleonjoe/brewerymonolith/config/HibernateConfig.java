package com.younesleonjoe.brewerymonolith.config;

import java.util.Map;
import org.hibernate.Interceptor;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig implements HibernatePropertiesCustomizer {

  Interceptor orderHeaderInterceptor;

  @Override
  public void customize(Map<String, Object> hibernateProperties) {
    hibernateProperties.put("hibernate.ejb.interceptor", orderHeaderInterceptor);
  }
}
