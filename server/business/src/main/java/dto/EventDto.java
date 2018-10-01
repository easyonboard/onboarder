package dto;

import lombok.Data;

import java.util.Date;
import java.util.List;
@Data
public class EventDto {

    private Integer idEvent;
    private String titleEvent;
    private String overview;

    private List<UserDto> enrolledUsers;

    private UserDto contactPerson;

    private Integer maxEnrolledUsers;

    private LocationDto location;
    private String otherLocation;

    private String keywords;

    private Date eventDate;

    private String eventTime;

    private MeetingHallDto meetingHall;

    public EventDto() {

    }
}
