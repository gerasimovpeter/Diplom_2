import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.*;

public class CreatingAnOrderTest {
    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();

    private String token;
    private Order order;

    private Response response;

    @Test
    @DisplayName("Creating an order with an invalid ingredient hash")
    public void createOrderWithInvalidIngrHashShouldBeError() {
        order = Order.getOrderWrongHash();
        response = orderClient.createOrder(order, "token");
        response.then().assertThat().statusCode(500);
    }


    @Test
    @DisplayName("Creation of an order with authorization")
    public void createOrderWithAuth() {
        User user = User.createRandomUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
        response = orderClient.createOrder(Order.getOrderCorrect(), token);
        userClient.removeUser(token);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Creating an order with ingredients")
    public void createOrderWithIngr() {
        order = Order.getOrderCorrect();
        response = orderClient.createOrder(order, "token");
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Creating an order without authorization")
    public void createOrderWithoutAuth() {
        response = orderClient.createOrder(Order.getOrderCorrect(), "token");
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }
    @Test
    @DisplayName("Creating an order without ingredients")
    public void createOrderWithoutIngrShouldBeError() {
        response = orderClient.createOrder(Order.getOrderEmpty(), "token");
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and().statusCode(400);
    }



}
