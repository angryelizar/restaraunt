import domain.Order;

import java.util.List;

public class App {
    List<Order> restarauntOrders = RestaurantOrders.read("orders_100.json").getOrders();

    public App() {
        restarauntOrders.forEach(System.out::println);
    }
}
