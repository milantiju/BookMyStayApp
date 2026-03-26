import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

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

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

class RoomAllocationService {
    private Set<String> allocatedRoomIds;

    private Map<String, Set<String>> assignedRoomsByType;

    public RoomAllocationService() {
        allocatedRoomIds = new HashSet<>();
        assignedRoomsByType = new HashMap<>();
    }

    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        Map<String, Integer> availabilityMap = inventory.getRoomAvailability();

        int currentAvailability = availabilityMap.getOrDefault(roomType, 0);
        if (currentAvailability <= 0) {
            System.out.println("Booking failed for Guest: " + reservation.getGuestName() +
                    " - No availability for " + roomType);
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRoomIds.add(roomId);
        assignedRoomsByType.putIfAbsent(roomType, new HashSet<>());
        assignedRoomsByType.get(roomType).add(roomId);

        inventory.updateAvailability(roomType, currentAvailability - 1);

        System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() +
                ", Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {
        int nextNumber = 1;
        if (assignedRoomsByType.containsKey(roomType)) {
            nextNumber = assignedRoomsByType.get(roomType).size() + 1;
        }

        String generatedId = roomType + "-" + nextNumber;

        while (allocatedRoomIds.contains(generatedId)) {
            nextNumber++;
            generatedId = roomType + "-" + nextNumber;
        }

        return generatedId;
    }
}

public class HotelBookingApp {

    public static void main(String[] args) {
        System.out.println("Book My Stay App: Room Allocation");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService();

        queue.addRequest(new Reservation("Abhi", "Single"));
        queue.addRequest(new Reservation("Subha", "Single"));
        queue.addRequest(new Reservation("Vanmathi", "Suite"));

        System.out.println("\nRoom Allocation Processing");

        while (queue.hasPendingRequests()) {
            Reservation nextRequest = queue.getNextRequest();
            allocationService.allocateRoom(nextRequest, inventory);
        }

        System.out.println("\nPost-Allocation Inventory");
        for (Map.Entry<String, Integer> entry : inventory.getRoomAvailability().entrySet()) {
            System.out.println(entry.getKey() + " Rooms Remaining: " + entry.getValue());
        }
    }
}