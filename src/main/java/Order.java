import java.util.ArrayList;
import java.util.List;

public class Order {
    private List<String> ingredients = new ArrayList<>();
    public static Order getOrderWrongHash() {
        return new Order(List.of("1", "2"));
    }

    public static Order getOrderEmpty() {
        return new Order(List.of());
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public static Order getOrderCorrect() {
        return new Order(List.of("61c0c5a71d1f82001bdaaa74", "63df84ed9ed280001b2d12f1"));
    }
    public Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

}
