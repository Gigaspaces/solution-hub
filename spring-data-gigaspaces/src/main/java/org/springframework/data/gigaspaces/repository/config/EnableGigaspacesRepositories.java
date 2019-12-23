package org.springframework.data.gigaspaces.repository.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.gigaspaces.repository.support.GigaspacesRepositoryFactoryBean;

import java.lang.annotation.*;

/**
 * <p>Annotation to activate Gigaspaces repositories within Java context configuration.</p>
 * <p>Example:</p>
 * <blockquote><pre>
 * &#64;Configuration
 * &#64;EnableGigaspacesRepositories("com.yourcompany.foo.bar")
 * public class ContextConfiguration {
 *   // bean definitions
 * }
 * </pre></blockquote>
 *
 * @author Oleksiy_Dyagilev
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(GigaspacesRepositoriesRegistrar.class)
public @interface EnableGigaspacesRepositories {

    /**
     * Alias for the {@link #basePackages()} attribute. Allows for more concise annotation declarations e.g.:
     * {@code @EnableGigaspacesRepositories("org.my.pkg")} instead of {@code @EnableGigaspacesRepositories(basePackages="org.my.pkg")}.
     */
    String[] value() default {};

    /**
     * Base packages to scan for annotated components. {@link #value()} is an alias for (and mutually exclusive with) this
     * attribute. Use {@link #basePackageClasses()} for a type-safe alternative to String-based package names.
     */
    String[] basePackages() default {};

    /**
     * Type-safe alternative to {@link #basePackages()} for specifying the packages to scan for annotated components. The
     * package of each class specified will be scanned. Consider creating a special no-op marker class or interface in
     * each package that serves no purpose other than being referenced by this attribute.
     */
    Class<?>[] basePackageClasses() default {};

    /**
     * Specifies which types are eligible for component scanning. Further narrows the set of candidate components from
     * everything in {@link #basePackages()} to everything in the base packages that matches the given filter or filters.
     */
    ComponentScan.Filter[] includeFilters() default {};

    /**
     * Specifies which types are not eligible for component scanning.
     */
    ComponentScan.Filter[] excludeFilters() default {};

    /**
     * Returns the {@link org.springframework.beans.factory.FactoryBean} class to be used for each repository instance. Defaults to
     * {@link GigaspacesRepositoryFactoryBean}.
     */
    Class<?> repositoryFactoryBeanClass() default GigaspacesRepositoryFactoryBean.class;

    /**
     * Configures the location of properties file with the Spring Data named queries.
     */
    String namedQueriesLocation() default "";


    /**
     * Wires GigaSpaces bean by it's id. Use this when multiple GigaSpace objects are declared in the context.
     */
    String gigaspace() default "";

    /**
     * Returns the postfix to be used when looking up custom repository implementations. Defaults to {@literal Impl}. So
     * for a repository named {@code PersonRepository} the corresponding implementation class will be looked up scanning
     * for {@code PersonRepositoryImpl}.
     */
    String repositoryImplementationPostfix() default "Impl";

}