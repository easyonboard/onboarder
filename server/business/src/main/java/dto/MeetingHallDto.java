package dto;

import entity.Location;
import lombok.Data;

@Data
public class MeetingHallDto {

    private int idMeetingHall;
    private String hallName;
    private int capacity;
    private Location location;
    private int floor;

}
