@Configuration
@Import({OperationalRepositories.class, BillingRepositories.class})
public class ContextConfiguration {
    /* other beans declaration omitted */
}

@Configuration
@EnableXapRepositories(basePackages = "com.yourcompany.foo.bar.repository.operational", gigaspace = "operationalGSpace")
class OperationalRepositories {
    /**
     * @return embedded operational space configuration
     */
    @Bean
    public GigaSpace operationalGSpace() {
        return new GigaSpaceConfigurer(new UrlSpaceConfigurer("/./operational")).gigaSpace();
    }
}

@Configuration
@EnableXapRepositories(basePackages = "com.yourcompany.foo.bar.repository.billing", gigaspace = "billingGSpace")
class BillingRepositories {
    /**
     * @return proxy billing space configuration
     */
    @Bean
    public GigaSpace billingGSpace() {
        return new GigaSpaceConfigurer(new UrlSpaceConfigurer("jini://*/*/billing")).gigaSpace();
    }
}