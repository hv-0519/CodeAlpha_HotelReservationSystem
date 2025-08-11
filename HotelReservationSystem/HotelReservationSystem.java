import java.io.*;
import java.util.*;

public class HotelReservationSystem {
    private static List<Room> rooms = new ArrayList<>();
    private static List<Reservation> reservations = new ArrayList<>();
    private static final String reservationFile = "reservations.txt";

    public static void main(String[] args) {
        initializeRooms();
        loadReservations();

        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nHotel Reservation System");
            System.out.println("1. View Rooms");
            System.out.println("2. Make Reservation");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Reservations");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int option = sc.nextInt();
            sc.nextLine();

            switch (option) {
                case 1:
                    displayRooms();
                    break;
                case 2:
                    makeReservation(sc);
                    break;
                case 3:
                    cancelReservation(sc);
                    break;
                case 4:
                    displayReservations();
                    break;
                case 5:
                    saveReservations();
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        sc.close();
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard"));
        rooms.add(new Room(102, "Standard"));
        rooms.add(new Room(201, "Deluxe"));
        rooms.add(new Room(202, "Deluxe"));
        rooms.add(new Room(301, "Suite"));
    }

    private static void loadReservations() {
        try (BufferedReader br = new BufferedReader(new FileReader(reservationFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                Reservation r = Reservation.fromString(line);
                reservations.add(r);
                for (Room room : rooms) {
                    if (room.getRoomNumber() == r.getRoomNumber()) {
                        room.bookRoom();
                    }
                }
            }
        } catch (IOException e) {
            // no reservations file found or error reading - ignore
        }
    }

    private static void saveReservations() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(reservationFile))) {
            for (Reservation r : reservations) {
                bw.write(r.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving reservations: " + e.getMessage());
        }
    }

    private static void displayRooms() {
        System.out.println("Rooms:");
        rooms.forEach(System.out::println);
    }

    private static void makeReservation(Scanner sc) {
        System.out.print("Enter guest name: ");
        String name = sc.nextLine();

        System.out.println("Available rooms:");
        rooms.stream().filter(r -> !r.isBooked()).forEach(System.out::println);

        System.out.print("Enter room number to book: ");
        int roomNum = sc.nextInt();
        sc.nextLine();

        Optional<Room> roomOpt = rooms.stream()
                .filter(r -> r.getRoomNumber() == roomNum && !r.isBooked())
                .findFirst();

        if (roomOpt.isPresent()) {
            roomOpt.get().bookRoom();
            reservations.add(new Reservation(name, roomNum));
            System.out.println("Room " + roomNum + " booked for " + name);
        } else {
            System.out.println("Room not available.");
        }
    }

    private static void cancelReservation(Scanner sc) {
    System.out.print("Enter room number to cancel reservation: ");
    int roomNumber = sc.nextInt();
    sc.nextLine();

    Iterator<Reservation> iter = reservations.iterator();
    boolean found = false;

    while (iter.hasNext()) {
        Reservation r = iter.next();
        if (r.getRoomNumber() == roomNumber) {
            iter.remove();
            rooms.stream()
                    .filter(room -> room.getRoomNumber() == roomNumber)
                    .findFirst()
                    .ifPresent(Room::cancelBooking);
            System.out.println("Reservation cancelled for room number " + roomNumber);
            found = true;
            break;
        }
    }
    if (!found) {
        System.out.println("No reservation found for room number " + roomNumber);
    }
}


    private static void displayReservations() {
        if (reservations.isEmpty()) {
            System.out.println("No reservations.");
        } else {
            System.out.println("Current Reservations:");
            reservations.forEach(r -> System.out.println("Guest: " + r.getGuestName() + ", Room: " + r.getRoomNumber()));
        }
    }
}
