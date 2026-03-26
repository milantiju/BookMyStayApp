import java.util.LinkedList;
import java.util.Queue;

class Reservation {
    private String guestName;

    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    @Override
    public String toString() {
        return "Guest: " + guestName + " | Requested Room: " + roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println(">> Queued: " + reservation.getGuestName() + " for a " + reservation.getRoomType());
    }

    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}

public class HotelBookingApp {
    public static void main(String[] args) {
        System.out.println("=== Book My Stay App: Booking Request Intake ===");

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        System.out.println("Accepting Booking Requests");

        bookingQueue.addRequest(new Reservation("Alice", "Suite Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Single Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Double Room"));
        bookingQueue.addRequest(new Reservation("Diana", "Suite Room"));

        System.out.println("\nProcessing Pending Requests (FIFO Order)");

        int position = 1;
        while (bookingQueue.hasPendingRequests()) {
            Reservation nextUp = bookingQueue.getNextRequest();
            System.out.println("Processing Request #" + position + " -> " + nextUp.toString());
            position++;
        }

        System.out.println("\nAll pending requests have been processed.");
    }
}