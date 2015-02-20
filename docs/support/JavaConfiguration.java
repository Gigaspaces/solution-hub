@Configuration
@EnableXapRepositories("org.xap.repositories")
public class ContextConfiguration {
    /**
     * Builds a space instance with settings that allow it to connect to the 'space'.
     */
    @Bean
    public SpaceClient spaceClient() throws FinderException {
        ISpaceProxy iSpace = (ISpaceProxy) SpaceFinder.find("jini://*/*/space");
        SpaceClient gigaSpace = new SpaceClient();
        gigaSpace.setSpace(iSpace);
        return gigaSpace;
    }
}