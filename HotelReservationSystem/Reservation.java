public class Reservation {
    private String guestName;
    private int roomNumber;

    public Reservation(String guestName, int roomNumber) {
        this.guestName = guestName;
        this.roomNumber = roomNumber;
    }

    public String getGuestName() {
        return guestName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    @Override
    public String toString() {
        return guestName + "," + roomNumber;
    }

    public static Reservation fromString(String line) {
        String[] parts = line.split(",");
        return new Reservation(parts[0], Integer.parseInt(parts[1]));
    }
}
