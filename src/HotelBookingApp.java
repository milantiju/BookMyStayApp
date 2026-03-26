import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

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
    private Queue<Reservation> requestQueue = new LinkedList<>();
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
class RoomInventory {
    private Map<String, Integer> roomAvailability = new HashMap<>();
    public RoomInventory() {
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
class RoomAllocationService {
    private Map<String, Integer> assignedCounts = new HashMap<>();
    public void allocateRoom(Reservation reservation, RoomInventory inventory) {
        String roomType = reservation.getRoomType();
        Map<String, Integer> availability = inventory.getRoomAvailability();
        int currentCount = availability.getOrDefault(roomType, 0);
        if (currentCount > 0) {
            int nextId = assignedCounts.getOrDefault(roomType, 0) + 1;
            assignedCounts.put(roomType, nextId);
            String roomId = roomType + "-" + nextId;
            inventory.updateAvailability(roomType, currentCount - 1);
            System.out.println("Booking confirmed for Guest: " + reservation.getGuestName() + ", Room ID: " + roomId);
        } else {
            System.out.println("Booking failed for Guest: " + reservation.getGuestName() + " - " + roomType + " sold out.");
        }
    }
}
class ConcurrentBookingProcessor implements Runnable {
    private BookingRequestQueue bookingQueue;
    private RoomInventory inventory;
    private RoomAllocationService allocationService;
    public ConcurrentBookingProcessor(
            BookingRequestQueue bookingQueue,
            RoomInventory inventory,
            RoomAllocationService allocationService
    ) {
        this.bookingQueue = bookingQueue;
        this.inventory = inventory;
        this.allocationService = allocationService;
    }
    @Override
    public void run() {
        while (true) {
            Reservation reservation = null;
            synchronized (bookingQueue) {
                if (bookingQueue.hasPendingRequests()) {
                    reservation = bookingQueue.getNextRequest();
                } else {
                    break;
                }
            }
            if (reservation != null) {
                synchronized (inventory) {
                    allocationService.allocateRoom(reservation, inventory);
                }
            }
        }
    }
}
public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("Concurrent Booking Simulation");
        BookingRequestQueue bookingQueue = new BookingRequestQueue();
        RoomInventory inventory = new RoomInventory();
        RoomAllocationService allocationService = new RoomAllocationService();
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Double"));
        bookingQueue.addRequest(new Reservation("Kural", "Suite"));
        bookingQueue.addRequest(new Reservation("Subha", "Single"));

        Thread t1 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );
        Thread t2 = new Thread(
                new ConcurrentBookingProcessor(bookingQueue, inventory, allocationService)
        );
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            System.out.println("Thread execution interrupted.");
        }

        System.out.println("\nRemaining Inventory:");
        Map<String, Integer> finalAvailability = inventory.getRoomAvailability();
        System.out.println("Single: " + finalAvailability.get("Single"));
        System.out.println("Double: " + finalAvailability.get("Double"));
        System.out.println("Suite: " + finalAvailability.get("Suite"));
    }
}