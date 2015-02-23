@Configuration
@EnableXapRepositories(value = "com.yourcompany.foo.bar", repositoryImplementationPostfix = "FooBar")
public class ContextConfiguration {
    // bean definitions omitted
}