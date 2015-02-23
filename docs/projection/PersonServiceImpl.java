@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public List<String> getAllNames() {
        Iterable<Person> personList = repository.findAll(Projection.projections("name"));
        // result processing ommited
    }

}