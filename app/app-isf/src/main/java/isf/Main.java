package isf;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        // Wczytanie pliku konfiguracyjnego
        String configContent = Files.readString(Paths.get("/path/to/store.json"));
        Gson gson = new Gson();
        Config config = gson.fromJson(configContent, Config.class);

        // Wczytanie pliku zamówień
        String ordersContent = Files.readString(Paths.get("/path/to/orders.json"));
        Type orderListType = new TypeToken<List<Order>>(){}.getType();
        List<Order> orders = gson.fromJson(ordersContent, orderListType);

        // Sortowanie zamówień według czasu ukończenia w kolejności rosnącej
        orders.sort(Comparator.comparing(Order::getCompleteBy));

        // Mapowanie identyfikatorów pracowników na pracowników
        Map<String, Employee> employeeMap = config.getEmployees().stream()
                .collect(Collectors.toMap(Employee::getPicker, e -> e));

        // Przydział zamówień do pracowników
        for (Order order : orders) {
            // Wybór pracownika z najwcześniejszym wolnym czasem, który ma wystarczająco dużo czasu na zakończenie zamówienia przed jego czasem ukończenia
            Optional<Employee> employeeOptional = employeeMap.values().stream()
                    .filter(e -> e.getPickingEndtime().isBefore(order.getCompleteBy().minus(order.getPickingTime())))
                    .min(Comparator.comparing(Employee::getPickingEndtime));

            if (employeeOptional.isPresent()) {
                Employee employee = employeeOptional.get();
                // Przypisanie zamówienia do pracownika i aktualizacja harmonogramu pracy
                employee.addOrder(order);
            } else {
                System.out.println("Nie znaleziono pracownika do przydziału zamówienia " + order.getOrderId());
            }
        }

        // Wyświetlenie harmonogramu pracy pracowników
        for (Employee employee : employeeMap.values()) {
            System.out.println("Harmonogram pracy pracownika " + employee.getPicker() + ":");
            for (Order order : employee.getOrders()) {
                System.out.println(order.getOrderId() + " od " + order.getCompleteBy().minus(order.getPickingTime())
                        + " do " + order.getCompleteBy() + ", wartość zamówienia: " + order.getOrderValue());
            }
        }
    }
}

