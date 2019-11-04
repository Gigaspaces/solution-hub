@Configuration
@EnableGigaspacesRepositories("com.yourcompany.foo.bar")
public class ContextConfiguration {
    
    @Bean
    public AnyClassHere personRepositoryImpl() {
        // further configuration
    }
    
    // other bean definitions omitted
}