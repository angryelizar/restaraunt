import domain.Order;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    List<Order> restarauntOrders = RestaurantOrders.read("orders_100.json").getOrders();

    public App() {
//        System.out.println("----- Все заказы -----");
//        restarauntOrders.forEach(System.out::println);
//        System.out.println("----------------------");
//        System.out.println("------ Топ заказов по общему чеку -----");
//        getTheBiggestOrders(5).forEach(System.out::println);
//        System.out.println("---------------------------------------");
//        System.out.println("------ Самые маленькие заказы -----");
//        getTheSmallestOrders(5).forEach(System.out::println);
//        System.out.println("-----------------------------------");
        System.out.println("------ Заказы с доставкой на дом -----");
        getDeliveryOrders().forEach(System.out::println);
        System.out.println(getDeliveryOrders().size());
        System.out.println("--------------------------------------");
    }

    public List<Order> getTheBiggestOrders(int numberOfOrders) {
        return restarauntOrders.stream()
                .sorted(Comparator.comparing(Order::getTotal).reversed())
                .limit(numberOfOrders)
                .collect(Collectors.toList());
    }

    public List<Order> getTheSmallestOrders(int numberOfOrders) {
        return restarauntOrders.stream()
                .sorted(Comparator.comparing(Order::getTotal))
                .limit(numberOfOrders)
                .collect(Collectors.toList());
    }

    public List<Order> getDeliveryOrders() {
        return restarauntOrders.stream()
                .filter(Order::isHomeDelivery)
                .collect(Collectors.toList());
    }

}
