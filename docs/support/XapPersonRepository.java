@Repository
public class XapPersonRepository implements PersonRepository {

    @Autowired
    private SpaceClient spaceClient;

    public void create(Person person) {
        spaceClient.write(person);
    }

    public List<Person> findById(String personId) {
        return spaceClient.read(new IdQuery<>(Person.class, personId));
    }

    ...

}