@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public List<String> getAllNames() {
        Iterable<Person> personList = repository.findAll(null, QueryDslProjection.projection(QPerson.person.name));
        // result processing ommited
    }

}