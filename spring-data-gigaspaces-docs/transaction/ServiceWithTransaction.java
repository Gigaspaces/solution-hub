@Service
public class PersonServiceImpl implements PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Transactional
    public void transactionalMethod(Person person) {
        ...
    }

}