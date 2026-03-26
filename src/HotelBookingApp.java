import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

class RoomInventory {
    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
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

class CancellationService {
    private Stack<String> releasedRoomIds;
    private Map<String, String> reservationRoomTypeMap;
    public CancellationService() {
        releasedRoomIds = new Stack<>();
        reservationRoomTypeMap = new HashMap<>();
    }
    public void registerBooking(String reservationId, String roomType) {
        reservationRoomTypeMap.put(reservationId, roomType);
    }
    public void cancelBooking(String reservationId, RoomInventory inventory) {
        if (!reservationRoomTypeMap.containsKey(reservationId)) {
            System.out.println("Error: Reservation ID not found or already cancelled.");
            return;
        }
        String roomType = reservationRoomTypeMap.get(reservationId);
        reservationRoomTypeMap.remove(reservationId);

        releasedRoomIds.push(reservationId);

        Map<String, Integer> availability = inventory.getRoomAvailability();
        int currentCount = availability.getOrDefault(roomType, 0);
        inventory.updateAvailability(roomType, currentCount + 1);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }
    public void showRollbackHistory() {
        System.out.println("\nRollback History (Most Recent First):");
        if (releasedRoomIds.isEmpty()) {
            System.out.println("No cancelled bookings.");
            return;
        }
        for (int i = releasedRoomIds.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + releasedRoomIds.get(i));
        }
    }
}
public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Booking Cancellation");

        RoomInventory inventory = new RoomInventory();
        CancellationService cancellationService = new CancellationService();

        String reservationId = "Single-1";
        String roomType = "Single";
        cancellationService.registerBooking(reservationId, roomType);

        cancellationService.cancelBooking(reservationId, inventory);

        cancellationService.showRollbackHistory();

        int updatedSingleAvailability = inventory.getRoomAvailability().get("Single");
        System.out.println("\nUpdated Single Room Availability: " + updatedSingleAvailability);
    }
}