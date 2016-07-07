package com.epam.rft.atsy.persistence.repositories;

import com.epam.rft.atsy.persistence.configuration.PersistenceConfiguration;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("integration")
@ContextConfiguration(classes = PersistenceConfiguration.class, initializers = RepositoryITApplciationContextInitializer.class)
@Transactional
@SqlConfig(encoding = "UTF-8")
public abstract class AbstractRepositoryIT {
}
