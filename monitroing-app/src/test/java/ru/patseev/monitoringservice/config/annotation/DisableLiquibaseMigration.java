package ru.patseev.monitoringservice.config.annotation;

import org.springframework.boot.test.mock.mockito.MockBean;
import ru.patseev.monitoringservice.migration.impl.LiquibaseMigration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@MockBean(LiquibaseMigration.class)
public @interface DisableLiquibaseMigration {
}
