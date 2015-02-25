public interface PersonRepository extends XapRepository<Person, String> {

    // these methods throw an UnsupportedOperationException when called

    List<Person> findByNameIgnoreCase(String name);

    List<Person> findByNameExists(boolean exists);

    List<Person> findByAgeIsNear(Integer nearAge);

    List<Person> findByAgeIsWithin(Integer minAge, Integer maxAge);

}