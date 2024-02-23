package ru.patseev.auditstarter.annotation;

import org.springframework.context.annotation.Import;
import ru.patseev.auditstarter.config.AuditConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to enable auditing in the application.
 * When this annotation is applied, the audit configuration class AuditConfig will be imported.
 */
@Target(ElementType.TYPE)
@Import(AuditConfig.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnableAudit {
}

