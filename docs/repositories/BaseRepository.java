@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends GigaspacesRepository<T, ID> {

    // you can define methods that apply to all other repositories

    T findByName(String name);

    ...

}