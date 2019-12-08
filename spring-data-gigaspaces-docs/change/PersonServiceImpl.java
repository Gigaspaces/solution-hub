@Service
public class PersonServiceImpl implements PersonService {
    @Autowired
    private PersonRepository repository;

    public void increaseAgeByName(String name) {
        repository.change(
                QPerson.person.name.eq(name),
                QChangeSet.changeSet().increment(QPerson.person.age, 1)
        );
    }

}