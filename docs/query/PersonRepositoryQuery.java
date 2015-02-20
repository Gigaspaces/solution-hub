public interface PersonRepository extends XapRepository<Person, String> {

    @Query("name = ? order by name asc")
    List<Person> findByNameOrdered(String name);

}