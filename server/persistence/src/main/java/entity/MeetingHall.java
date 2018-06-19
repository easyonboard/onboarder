package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class MeetingHall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idMeetingHall;

    @Column
    private String hallName;

    @Column
    private int capacity;

    @Column
    private int floor;

    @ManyToOne
    private Location location;

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
