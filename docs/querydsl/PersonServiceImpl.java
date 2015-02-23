@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public Iterable<Person> getByAge(Integer minAge, Integer maxAge) {
        return repository.findAll(
                QPerson.person.name.isNotNull().and(QPerson.person.age.between(minAge, maxAge))
        );
    }

}