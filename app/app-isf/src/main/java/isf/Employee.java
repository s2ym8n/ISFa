package isf;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Employee {
    private String picker;
    private LocalTime pickingStartTime;
    private LocalTime pickingEndTime;
    private Order order;
    private List<Order> orders;

    public Employee(String picker, LocalTime pickingStartTime, LocalTime pickingEndTime) {
        this.picker = picker;
        this.pickingStartTime = pickingStartTime;
        this.pickingEndTime = pickingEndTime;
    }

    public String getPicker() {
        return picker;
    }

    public LocalTime getPickingStartTime(){
        return pickingStartTime;
    }

    public LocalTime getPickingEndTime(){
        return pickingEndTime;
    }

    public Order addOrder(){
        return this.order;
    }

    public List<Order> getOrders(){
        return this.orders;
    }
}
