@Configuration
@EnableXapRepositories(value = "com.yourcompany.foo.bar", namedQueriesLocation = "classpath:named-queries.properties")
public class ContextConfiguration {
    // bean definitions omitted
}