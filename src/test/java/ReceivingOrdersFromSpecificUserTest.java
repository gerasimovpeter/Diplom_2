import io.restassured.response.Response;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class ReceivingOrdersFromSpecificUserTest {
    private Response response;
    private Order order;

    private final UserClient userClient = new UserClient();
    private final OrderClient orderClient = new OrderClient();

    private String token;
    @Test
    @DisplayName("Receiving orders from an authorized user")
    public void shouldGetOrdersWithAuthUser() {
        User user = User.createRandomUser();
        response = userClient.createUser(user);
        token = response.then().extract().body().path("accessToken");
        order = Order.getOrderCorrect();
        response = orderClient.createOrder(order, token);
        response = orderClient.createOrder(order, token);
        response = orderClient.getUserOrders(token);
        userClient.removeUser(token);
        response.then().assertThat().body("orders", notNullValue())
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Receiving orders from an unauthorized user")
    public void getOrdersWithUnauthorizedShouldBeError() {
        order = Order.getOrderCorrect();
        response = orderClient.createOrder(order, "token");
        response = orderClient.createOrder(order, "token");
        response = orderClient.getUserOrders("token");
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and().statusCode(401);
    }


}
