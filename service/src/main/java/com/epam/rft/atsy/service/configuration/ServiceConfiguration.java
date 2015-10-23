package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.persistence.configuration.PersistenceConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by Ikantik.
 */
@Configuration
@Import({PersistenceConfiguration.class})
public class ServiceConfiguration {
}
