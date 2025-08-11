public class Room {
    private String type;
    private int roomNumber;
    private boolean isBooked;

    public Room(int roomNumber, String type) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.isBooked = false;
    }

    public String getType() {
        return type;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void bookRoom() {
        isBooked = true;
    }

    public void cancelBooking() {
        isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ") - " + (isBooked ? "Booked" : "Available");
    }
}
