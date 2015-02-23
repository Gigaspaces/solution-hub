public interface PersonRepository extends XapRepository<Person, String> {

    List<Person> findByName(String name, Projection projection);

}