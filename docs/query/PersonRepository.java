public interface PersonRepository extends XapRepository<Person, String> {

    // you can query objects with exact field match
    List<Person> findByName(String name);

    List<Person> findByAge(Integer age);

    // you can use ranged of search for number fields
    List<Person> findByAgeBetween(Integer minAge, Integer maxAge);

    // you can use boolean expressions to define complex conditions
    List<Person> findByNameAndAge(String name, Integer age);

    List<Person> findByNameOrAge(String name, Integer age);

}