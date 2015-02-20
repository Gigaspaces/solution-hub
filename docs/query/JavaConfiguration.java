@Configuration
@EnableXapRepositories(value = "org.xap.repositories", namedQueriesLocation = "classpath:named-queries.properties")
public class ContextConfiguration {
    // bean definitions omitted
}