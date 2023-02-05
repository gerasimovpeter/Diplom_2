import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import static org.hamcrest.Matchers.*;

public class ChangingUserDataTest {

    private Response response;
    private String email;
    private String password;
    private String name;
    private final UserClient userClient = new UserClient();
    private String token;
    private User user;

    @Before
    public void setup() {
        user = User.createRandomUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Change account password without authorization")
    public void updatePasswordShouldBeError() {
        password = user.getPassword();
        user.setPassword(password + "password");
        response = userClient.updateUser(user, "null");
        user.setPassword(password);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401);
    }

    @Test
    @DisplayName("Changing the email and name of the account with authorization")
    public void shouldUpdateEmailAndName() {
        email = user.getEmail();
        name = user.getName();
        user.setEmail(email + "email");
        user.setName(name + "name");
        response = userClient.updateUser(user, token);
        user.setEmail(email);
        user.setName(name);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Change mail and account name without authorization")
    public void updateEmailAndNameShouldBeError() {
        email = user.getEmail();
        name = user.getName();
        user.setEmail(email + "email");
        user.setName(name + "name");
        response = userClient.updateUser(user, "null");
        user.setEmail(email);
        user.setName(name);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401);
    }
    @Test
    @DisplayName("Change account password with authorization")
    public void shouldUpdatePassword() {
        password = user.getPassword();
        user.setPassword(password + "password");
        response = userClient.updateUser(user, token);
        user.setPassword(password);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @After
    public void teardown() {
        userClient.removeUser(token);
    }
}

