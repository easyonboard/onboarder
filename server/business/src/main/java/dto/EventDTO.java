package dto;

import org.apache.tomcat.jni.Time;

import java.util.Date;
import java.util.List;

public class EventDTO {

    private Integer idEvent;
    private String titleEvent;
    private String overview;

    private List<UserDTO> enrolledUsers;

    private List<UserDTO> contactPerson;

    private Integer maxEnrolledUsers;

    private LocationDto location;

    private String keywords;

    private Date eventDate;

    private String eventTime;

    private MeetingHallDto meetingHall;

    public EventDTO() {
    }

    public EventDTO(Integer idEvent, String titleEvent, String overview, List<UserDTO> enrolledUsers, List<UserDTO> contactPerson, Integer maxEnrolledUsers, LocationDto location, String keywords, Date eventDate, MeetingHallDto meetingHall) {
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

    public List<UserDTO> getEnrolledUsers() {
        return enrolledUsers;
    }

    public void setEnrolledUsers(List<UserDTO> enrolledUsers) {
        this.enrolledUsers = enrolledUsers;
    }

    public List<UserDTO> getContactPersons() {
        return contactPerson;
    }

    public void setContactPersons(List<UserDTO> contactPerson) {
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
