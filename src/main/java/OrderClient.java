import io.qameta.allure.Step;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class OrderClient {
    @Step("Create an order")
    public Response createOrder(Order order, String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Configuration.URL)
                .body(order)
                .post(Configuration.ORDERS);
    }

    @Step("Get user orders")
    public Response getUserOrders(String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(Configuration.URL)
                .get(Configuration.ORDERS);
    }
}
