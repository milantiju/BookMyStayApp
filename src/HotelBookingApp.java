import java.util.HashMap;
import java.util.Map;

class Room {
    private String type;
    private int beds;
    private int sizeSqft;
    private double pricePerNight;

    public Room(String type, int beds, int sizeSqft, double pricePerNight) {
        this.type = type;
        this.beds = beds;
        this.sizeSqft = sizeSqft;
        this.pricePerNight = pricePerNight;
    }

    public String getDetails() {
        return String.format("%-6s | Beds: %d | Size: %d sqft | Price: %.2f/night",
                type, beds, sizeSqft, pricePerNight);
    }
}

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("Single", 5);
        roomAvailability.put("Double", 3);
        roomAvailability.put("Suite", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}

class RoomSearchService {

    public void searchAvailableRooms(RoomInventory inventory, Room singleRoom, Room doubleRoom, Room suiteRoom) {
        System.out.println("\nSearching Available Rooms");

        // Retrieve availability data without modifying it
        Map<String, Integer> availability = inventory.getRoomAvailability();

        boolean foundAny = false;

        if (availability.containsKey("Single") && availability.get("Single") > 0) {
            System.out.println(singleRoom.getDetails() + " | Available: " + availability.get("Single"));
            foundAny = true;
        }

        if (availability.containsKey("Double") && availability.get("Double") > 0) {
            System.out.println(doubleRoom.getDetails() + " | Available: " + availability.get("Double"));
            foundAny = true;
        }

        if (availability.containsKey("Suite") && availability.get("Suite") > 0) {
            System.out.println(suiteRoom.getDetails() + " | Available: " + availability.get("Suite"));
            foundAny = true;
        }

        if (!foundAny) {
            System.out.println("Sorry, no rooms are currently available.");
        }
    }
}

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Book My Stay App: Room Search & Availability");

        RoomInventory inventory = new RoomInventory();

        Room singleRoom = new Room("Single", 1, 250, 1500.0);
        Room doubleRoom = new Room("Double", 2, 400, 2500.0);
        Room suiteRoom = new Room("Suite", 3, 750, 5000.0);

        RoomSearchService searchService = new RoomSearchService();

        System.out.println(">> Guest performs an initial search:");
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);

        System.out.println("\n>> Simulating booking... All Suite rooms and Double rooms get booked.");
        inventory.updateAvailability("Suite", 0);
        inventory.updateAvailability("Double", 0);

        System.out.println("\n>> Guest performs another search:");
        searchService.searchAvailableRooms(inventory, singleRoom, doubleRoom, suiteRoom);
    }
}