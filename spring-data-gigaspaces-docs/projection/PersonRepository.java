public interface PersonRepository extends GigaspacesRepository<Person, String> {

    List<Person> findByName(String name, Projection projection);

}