import domain.Customer;
import domain.Item;
import domain.Order;

import java.util.*;
import java.util.stream.Collectors;

public class App {
    List<Order> restaurantOrders = RestaurantOrders.read("orders_100.json").getOrders();

    public App() {
//        System.out.println("----- Все заказы -----");
//        restaurantOrders.forEach(System.out::println);
//        System.out.println("----------------------");
//        System.out.println("------ Топ заказов по общему чеку -----");
//        getTheBiggestOrders(5).forEach(System.out::println);
//        System.out.println("---------------------------------------");
//        System.out.println("------ Самые маленькие заказы -----");
//        getTheSmallestOrders(5).forEach(System.out::println);
//        System.out.println("-----------------------------------");
//        System.out.println("------ Заказы с доставкой на дом -----");
//        getDeliveryOrders().forEach(System.out::println);
//        System.out.println("--------------------------------------");
//        System.out.println("--  Самый дорогой заказ на дом  --");
//        getDeliveryTopOrder().forEach(System.out::println);
//        System.out.println("--------------------------------------");
//        System.out.println("- Самый маленький заказ на дом -");
//        getDeliverySmallestOrder().forEach(System.out::println);
//        System.out.println("--------------------------------------");
//        System.out.println("--  Ищем заказы в определенном ценовом диапазоне  --");
//        getOrdersInRange(1,10).forEach(System.out::println);
//        System.out.println("----------------------------------------------------");
//        System.out.println("------------  Общая сумма заказов  -------------------");
//        System.out.println("$" + Math.round(getTotalSumOfOrders()));
//        System.out.println("----------------------------------------------------");
//        System.out.println("--- Уникальные e-mail клиентов ---");
//        getCustomersEmails().forEach(System.out::println);
//        System.out.println("----------------------------------");
//        System.out.println("--- Уникальные клиенты и их заказы ---");
//        getGroupingOrdersByCustomer();
//        System.out.println("--------------------------------------");
//        System.out.println("--- Уникальные клиенты и общая сумма их заказов ---");
//        getCustomersAndTotalSum();
//        System.out.println("---------------------------------------------------");
//        System.out.println("---   Находим заказчика с самым большим чеком   ---");
//        getCustomerWithBiggestSum();
//        System.out.println("---------------------------------------------------");
//        System.out.println("---  Находим заказчика с самым маленьким чеком  ---");
//        getCustomerWithSmallestSum();
//        System.out.println("---------------------------------------------------");
//        System.out.println("------- Товары и их количество продаж ------");
//        getItemsAndCountOfSales();
//        System.out.println("--------------------------------------------");
    }

    public List<Order> getTheBiggestOrders(int numberOfOrders) {
        return restaurantOrders.stream()
                .sorted(Comparator.comparing(Order::getTotal).reversed())
                .limit(numberOfOrders)
                .collect(Collectors.toList());
    }

    public List<Order> getTheSmallestOrders(int numberOfOrders) {
        return restaurantOrders.stream()
                .sorted(Comparator.comparing(Order::getTotal))
                .limit(numberOfOrders)
                .collect(Collectors.toList());
    }

    public List<Order> getDeliveryOrders() {
        return restaurantOrders.stream()
                .filter(Order::isHomeDelivery)
                .collect(Collectors.toList());
    }

    public List<Order> getDeliveryTopOrder() {
        return getDeliveryOrders().stream()
                .max(Comparator.comparingDouble(Order::getTotal))
                .stream().collect(Collectors.toList());
    }

    public List<Order> getDeliverySmallestOrder() {
        return getDeliveryOrders().stream()
                .min(Comparator.comparingDouble(Order::getTotal))
                .stream().collect(Collectors.toList());
    }

    public List<Order> getOrdersInRange(int minOrderTotal, int maxOrderTotal) {
        return restaurantOrders.stream()
                .sorted(Comparator.comparingDouble(Order::getTotal))
                .dropWhile(o -> o.getTotal() < minOrderTotal)
                .takeWhile(o -> o.getTotal() < maxOrderTotal)
                .collect(Collectors.toList());
    }

    public double getTotalSumOfOrders() {
        return restaurantOrders.stream()
                .mapToDouble(Order::getTotal)
                .sum();
    }

    public List<String> getCustomersEmails() {
        return restaurantOrders.stream()
                .map(Order::getCustomer)
                .map(Customer::getEmail)
                .distinct()
                .collect(Collectors.toList());
    }

    public void getGroupingOrdersByCustomer() {
        var customersAndOrders = restaurantOrders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.mapping(Order::getItems, Collectors.toList())));
        System.out.println(customersAndOrders);
    }

    public void getCustomersAndTotalSum() {
        var customersAndTotalSum = restaurantOrders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.summingDouble(Order::getTotal)));

        System.out.println(customersAndTotalSum);
    }

    public void getCustomerWithBiggestSum() {
        var customersAndTotalSum = restaurantOrders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.summingDouble(Order::getTotal)));
        var keysAndValues = customersAndTotalSum.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        var result = keysAndValues.entrySet().stream()
                .reduce((first, second) -> second)
                .orElse(null);
        System.out.println(result);
    }

    public void getCustomerWithSmallestSum() {
        var customersAndTotalSum = restaurantOrders.stream()
                .collect(Collectors.groupingBy(Order::getCustomer,
                        Collectors.summingDouble(Order::getTotal)));
        var keysAndValues = customersAndTotalSum.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
        var result = keysAndValues.entrySet().stream()
                .findFirst()
                .orElse(null);
        System.out.println(result);
    }

    public void getItemsAndCountOfSales() {
        var soldItems = restaurantOrders.stream()
                .flatMap(m -> m.getItems().stream())
                .collect(Collectors.toList());
        var result = soldItems.stream()
                        .collect(Collectors.groupingBy(Item::getName, Collectors.summingInt(Item::getAmount)));
        System.out.println(result);
    }

}
