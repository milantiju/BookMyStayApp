import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Service {
    private String serviceName;

    private double cost;

    public Service(String serviceName, double cost) {
        this.serviceName = serviceName;
        this.cost = cost;
    }
    public String getServiceName() {
        return serviceName;
    }
    public double getCost() {
        return cost;
    }
}
class AddOnServiceManager {
    private Map<String, List<Service>> servicesByReservation;

    public AddOnServiceManager() {
        servicesByReservation = new HashMap<>();
    }

    public void addService(String reservationId, Service service) {
        servicesByReservation.putIfAbsent(reservationId, new ArrayList<>());

        servicesByReservation.get(reservationId).add(service);
    }
    public double calculateTotalServiceCost(String reservationId) {
        // Retrieve the list of services, defaulting to an empty list if none exist
        List<Service> services = servicesByReservation.getOrDefault(reservationId, new ArrayList<>());

        double totalCost = 0.0;
        for (Service service : services) {
            totalCost += service.getCost();
        }

        return totalCost;
    }
}

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Add-On Service Selection");

        AddOnServiceManager serviceManager = new AddOnServiceManager();

        Service breakfast = new Service("Breakfast", 500.0);
        Service spa = new Service("Spa", 1000.0);

        String reservationId = "Single-1";

        serviceManager.addService(reservationId, breakfast);
        serviceManager.addService(reservationId, spa);

        double totalCost = serviceManager.calculateTotalServiceCost(reservationId);

        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Total Add-On Cost: " + totalCost);
    }
}