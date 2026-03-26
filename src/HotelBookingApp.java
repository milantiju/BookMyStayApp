import java.io.*;
import java.util.*;


class InventoryState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;

    public InventoryState() {
        inventory = new LinkedHashMap<>();
    }
}

class PersistenceService {

    private static final String FILE_NAME = "inventory.ser";

    public static InventoryState load() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("System Recovery");
            System.out.println("No valid inventory data found. Starting fresh.");
            return createDefaultInventory();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            System.out.println("System Recovery");
            return (InventoryState) ois.readObject();
        } catch (Exception e) {
            System.out.println("System Recovery");
            System.out.println("No valid inventory data found. Starting fresh.");
            return createDefaultInventory();
        }
    }

    public static void save(InventoryState state) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(state);
            System.out.println("Inventory saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving inventory.");
        }
    }

    private static InventoryState createDefaultInventory() {
        InventoryState state = new InventoryState();
        state.inventory.put("Single", 5);
        state.inventory.put("Double", 3);
        state.inventory.put("Suite", 2);
        return state;
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {

        InventoryState state = PersistenceService.load();

        System.out.println();
        System.out.println("Current Inventory:");
        for (Map.Entry<String, Integer> entry : state.inventory.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        PersistenceService.save(state);
    }
}