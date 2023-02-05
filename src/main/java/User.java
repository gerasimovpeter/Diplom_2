import org.apache.commons.lang3.RandomStringUtils;

public class User {
    private String name;
    private String email;
    private String password;

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public static User getExistUser() {
        return new User("peter-test@test.ru", "Qwe12345", "peter74");
    }

    public static User createRandomUser() {
        return new User(RandomStringUtils.randomAlphanumeric(15) + "@test.ru", "Qwe12345", "TestName");
    }
    public static User createUserWithEmptyPassword() {
        return new User(RandomStringUtils.randomAlphanumeric(15) + "@test.ru", null, "TestName");
    }

    public static User createUserWithEmptyEmail() {
        return new User(null, "Qwe12345", RandomStringUtils.randomAlphabetic(10));
    }

    public static User createUserWithEmptyName() {
        return new User(RandomStringUtils.randomAlphanumeric(15) + "@test.ru", "Qwe12345", null);
    }



    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

