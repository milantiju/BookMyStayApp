import java.util.*;

abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;
    protected int totalRooms;
    protected int availableRooms;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight, int totalRooms) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
        this.totalRooms = totalRooms;
        this.availableRooms = totalRooms;
    }

    public void bookRoom() {
        if(availableRooms > 0) {
            availableRooms--;
            System.out.println("Room booked");
        }
        else {
            System.out.println("No rooms available");
        }
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available Rooms: " + availableRooms);
    }
}
class SingleRoom extends Room {
    public SingleRoom(int totalRooms) {
        super(1,250,1500.0, totalRooms);
    }
}
class DoubleRoom extends Room {
    public DoubleRoom(int totalRooms) {
        super(2,400,2500.0, totalRooms);
    }
}
class SuiteRoom extends Room {
    public SuiteRoom(int totalRooms) {
        super(3,750,5000.0, totalRooms);
    }
}
public class HotelBookingApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter number of Single Rooms: ");
        SingleRoom singleroom = new SingleRoom(scanner.nextInt());

        System.out.println("Enter number of Double Rooms: ");
        DoubleRoom doubleroom = new DoubleRoom(scanner.nextInt());

        System.out.println("Enter number of Suite Rooms: ");
        SuiteRoom suiteroom = new SuiteRoom(scanner.nextInt());

        while (true) {
            System.out.println("\nChoose Room Type to Book:");
            System.out.println("1. Single Room");
            System.out.println("2. Double Room");
            System.out.println("3. Suite Room");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    singleroom.bookRoom();
                    break;
                case 2:
                    doubleroom.bookRoom();
                    break;
                case 3:
                    suiteroom.bookRoom();
                    break;
                case 4:
                    System.out.println("Exiting system...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
            System.out.println("\nCurrent Hotel Inventory:");
            singleroom.displayRoomDetails();
            doubleroom.displayRoomDetails();
            suiteroom.displayRoomDetails();

        }
    }
}