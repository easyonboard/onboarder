package dto;

import java.util.Date;
import java.util.List;

public class EventDto {

    private Integer idEvent;
    private String titleEvent;
    private String overview;

    private List<UserDto> enrolledUsers;

    private List<UserDto> contactPerson;

    private Integer maxEnrolledUsers;

    private LocationDto location;

    private String keywords;

    private Date eventDate;

    private String eventTime;

    private MeetingHallDto meetingHall;

    public EventDto() {
    }

    public EventDto(Integer idEvent, String titleEvent, String overview, List<UserDto> enrolledUsers, List<UserDto> contactPerson, Integer maxEnrolledUsers, LocationDto location, String keywords, Date eventDate, MeetingHallDto meetingHall) {
        this.idEvent = idEvent;
        this.titleEvent = titleEvent;
        this.overview = overview;
        this.enrolledUsers = enrolledUsers;
        this.contactPerson = contactPerson;
        this.maxEnrolledUsers = maxEnrolledUsers;
        this.location = location;
        this.keywords = keywords;
        this.eventDate = eventDate;
        this.meetingHall=meetingHall;
    }

    public Integer getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }

    public String getTitleEvent() {
        return titleEvent;
    }

    public void setTitleEvent(String titleEvent) {
        this.titleEvent = titleEvent;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public List<UserDto> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<UserDto> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public List<UserDto> getContactPersons() {
        return contactPerson;
    }

    public void setContactPersons(List<UserDto> contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Integer getMaxEnrolledUsers() {
        return maxEnrolledUsers;
    }

    public void setMaxEnrolledUsers(Integer maxEnrolledUsers) {
        this.maxEnrolledUsers = maxEnrolledUsers;
    }

    public LocationDto getLocation() {
        return location;
    }

    public void setLocation(LocationDto location) {
        this.location = location;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventTime() {

        return eventTime;
    }

    public void setEventTime(String eventTime) {

        this.eventTime = eventTime;
    }

    public MeetingHallDto getMeetingHall() {
        return meetingHall;
    }

    public void setMeetingHall(MeetingHallDto meetingHall) {
        this.meetingHall = meetingHall;
    }
}
