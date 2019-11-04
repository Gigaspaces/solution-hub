public interface PersonRepository extends GigaspacesRepository<Person, String> {

    @Query("name = ? order by name asc")
    List<Person> findByNameOrdered(String name);

}