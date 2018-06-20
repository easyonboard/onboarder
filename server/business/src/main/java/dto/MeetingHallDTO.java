package dto;

import entity.Location;

public class MeetingHallDTO {

    private int idMeetingHall;
    private String hallName;
    private int capacity;
    private Location location;
    private int floor;

    public int getIdMeetingHall() {
        return idMeetingHall;
    }

    public void setIdMeetingHall(int idMeetingHall) {
        this.idMeetingHall = idMeetingHall;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
