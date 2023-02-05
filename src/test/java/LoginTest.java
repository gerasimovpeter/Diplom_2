import io.qameta.allure.Description;
import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import static org.hamcrest.Matchers.*;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;


public class LoginTest {
    private Response response;
    private User user;

    private final UserClient userClient = new UserClient();
    private String email;
    private String password;
    private String token;

    @Before
    public void setup() {
        user = User.createRandomUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("User authorization with all required fields filled in")
    @Description("Successful user authorization")
    public void shouldSuccessLogin() {
        response = userClient.loginUser(user = User.getExistUser());
        response.then().statusCode(200).assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("User authorization with incorrect username and password")
    @Description("Authorization of a user with an incorrect password, the response and statusCode are checked")
    public void loginWithInvalidPasswordAndEmail() {
        email = user.getEmail();
        user.setEmail("incorrect@test.com");
        password = user.getPassword();
        user.setPassword("incorrectPassword");
        response = userClient.loginUser(user, token);
        user.setEmail(email);
        user.setPassword(password);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401);
    }

    @After
    public void teardown() {
        userClient.removeUser(token);
    }

}
