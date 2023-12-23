import domain.Order;

import java.util.List;

public class App {
    List<Order> restarauntOrders = RestaurantOrders.read("orders_100.json").getOrders();

    private void start(){
        System.out.println(restarauntOrders);
    }
}
