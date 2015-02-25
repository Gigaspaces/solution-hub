public interface PersonRepository extends XapRepository<Person, String> {

    // all of these methods will throw an UnsupportedOperationException if called

    List<Person> findByNameIgnoreCase(String name);

    List<Person> findByNameExists(boolean exists);

    List<Person> findByAgeIsNear(Integer nearAge);

    List<Person> findByAgeIsWithin(Integer minAge, Integer maxAge);

}