package dto;

import java.util.Date;
import java.util.List;

public class EventDTO {

    private Integer idEvent;

    private String titleEvent;

    private String overview;

    private List<UserDTO> enrolledUsers;

    private UserDTO contactPerson;

    private Integer maxEnrolledUsers;

    private LocationDTO location;

    private String keywords;

    private Date eventDate;

    public EventDTO() {
    }

    public EventDTO(Integer idEvent, String titleEvent, String overview, List<UserDTO> enrolledUsers, UserDTO contactPerson, Integer maxEnrolledUsers, LocationDTO location, String keywords, Date eventDate) {
        this.idEvent = idEvent;
        this.titleEvent = titleEvent;
        this.overview = overview;
        this.enrolledUsers = enrolledUsers;
        this.contactPerson = contactPerson;
        this.maxEnrolledUsers = maxEnrolledUsers;
        this.location = location;
        this.keywords = keywords;
        this.eventDate = eventDate;
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

    public UserDTO getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(UserDTO contactPerson) {
        this.contactPerson = contactPerson;
    }

    public Integer getMaxEnrolledUsers() {
        return maxEnrolledUsers;
    }

    public void setMaxEnrolledUsers(Integer maxEnrolledUsers) {
        this.maxEnrolledUsers = maxEnrolledUsers;
    }

    public LocationDTO getLocation() {
        return location;
    }

    public void setLocation(LocationDTO location) {
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
}
