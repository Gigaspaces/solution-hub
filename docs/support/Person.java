@SpaceClass
public class Person {
    private Integer id;
    private String name;
    private Integer age;

    public Person() {
    }

    @SpaceId(autoGenerate = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // getters and setters for other fields are omitted
    
}