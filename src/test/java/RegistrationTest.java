import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class RegistrationTest {
    private Response response;
    private User user;
    private final UserClient userClient = new UserClient();

    @Test
    @DisplayName("Сreating a unique user")
    public void successRegistrationTest() {
        user = User.createRandomUser();
        response = userClient.createUser(user);
        String token = response.then().extract().body().path("accessToken");
        userClient.removeUser(token);
        response.then().assertThat().body("accessToken", notNullValue())
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Сreating a user who is already registered")
    public void existRegistrationTest() {
        user = User.getExistUser();
        response = userClient.createUser(user);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and().statusCode(403);
    }
    @Test
    @DisplayName("Creating a user with an empty required field Password")
    public void registrationWithEmptyPasswordTest() {
        user = User.createUserWithEmptyPassword();
        response = userClient.createUser(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    @DisplayName("Creating a user with an empty required field Email")
    public void registrationWithEmptyEmailTest() {
        user = User.createUserWithEmptyEmail();
        response = userClient.createUser(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    @DisplayName("Creating a user with an empty required field Name")
    public void registrationWithEmptyNameTest() {
        user = User.createUserWithEmptyName();
        response = userClient.createUser(user);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }
}
