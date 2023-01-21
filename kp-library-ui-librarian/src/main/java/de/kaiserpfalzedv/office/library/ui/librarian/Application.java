package de.kaiserpfalzedv.office.library.ui.librarian;

import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

/**
 * <p>The entry point of the Quarkus application.</p>
 *
 * <p>Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.</p>
 *
 * @author klenkes74 {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 1.0.0  2023-01-23
 */
@ApplicationScoped
@PWA(name = "Library Management", shortName = "Librarian")
@NpmPackage(value = "line-awesome", version = "1.3.0")
@NpmPackage(value = "@vaadin-component-factory/vcf-nav", version = "1.0.6")
public class Application implements AppShellConfigurator {
    @Produces
    public AccessAnnotationChecker accessAnnotationChecker() {
        return new AccessAnnotationChecker();
    }
}
