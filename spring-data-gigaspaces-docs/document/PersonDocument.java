public class PersonDocument extends SpaceDocument {
    public static final String TYPE_NAME = "Person";
    public static final String PROPERTY_ID = "id";
    public static final String PROPERTY_AGE = "age";
    // other properties omitted

    public PersonDocument() {
        super(TYPE_NAME);
    }

    public String getId() {
        return super.getProperty(PROPERTY_ID);
    }

    public PersonDocument setId(String id) {
        super.setProperty(PROPERTY_ID, id);
        return this;
    }

    public Integer getAge() {
        return super.getProperty(PROPERTY_AGE);
    }

    public PersonDocument setAge(Integer age) {
        super.setProperty(PROPERTY_AGE, age);
        return this;
    }

    // other properties accessors are omitted
}