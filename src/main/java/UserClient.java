import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import static io.restassured.RestAssured.*;

public class UserClient {
    @Step("Create a user")
    public Response createUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.URL)
                .body(user).post(Configuration.REGISTER);
    }

    @Step("Remove user")
    public void removeUser(String token) {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(Configuration.URL).delete(Configuration.USER);
    }
    @Step("Update User")
    public Response updateUser(User newUser, String token) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(Configuration.URL)
                .body(newUser).patch(Configuration.USER);
    }
    @Step("Login user")
    public Response loginUser(User user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(Configuration.URL)
                .body(user).post(Configuration.LOGIN);
    }

    @Step("Login user")
    public Response loginUser(User user, String token) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(Configuration.URL)
                .body(user).post(Configuration.LOGIN);
    }




}
