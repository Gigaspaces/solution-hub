@Configuration
public class ContextConfiguration {
    /**
     * Builds a space instance with settings that allow it start the embedded space with name 'space'.
     */
    @Bean
    public GigaSpace spaceClient() {
        UrlSpaceConfigurer urlSpaceConfigurer = new UrlSpaceConfigurer("/./space");
        return new GigaSpaceConfigurer(urlSpaceConfigurer).gigaSpace();
    }
}