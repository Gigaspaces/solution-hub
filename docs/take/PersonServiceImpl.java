@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public Person takeByName(String name) {
        return repository.takeOne(QPerson.person.name.eq(name));
    }

}