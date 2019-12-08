@Repository
public class GigaspacesPersonRepository implements PersonRepository {

    @Autowired
    private GigaSpace space;

    public void create(Person person) {
        space.write(person);
    }

    public List<Person> findById(String personId) {
        return space.readById(Person.class, personId);
    }

    ...

}