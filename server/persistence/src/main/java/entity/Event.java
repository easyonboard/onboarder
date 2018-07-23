package entity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEvent;

    @Column
    private String titleEvent;

    @Lob
    @Size(max = 500)
    @Column
    private String overview;

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "event_enrolledUsers", joinColumns = @JoinColumn(name = "idEvent"),
               inverseJoinColumns = @JoinColumn(name = "idUser"))
    private List<User> enrolledUsers;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private User contactPerson;

    @Column
    private Integer maxEnrolledUsers;

    @ManyToOne
    @JoinColumn(name = "idLocation")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "idMeetingHall")
    private MeetingHall meetingHall;

    @Column
    private String keywords;

    @Column
    private Date eventDate;

    @Column
    private String eventTime;

    public Event() {

    }

    public Event(String titleEvent, @Size(max = 500) String overview, List<User> enrolledUsers, User contactPerson,
                 Integer maxEnrolledUsers, Location location, String keywords, Date eventDate, String eventTime) {

        this.titleEvent = titleEvent;
        this.overview = overview;
        this.enrolledUsers = enrolledUsers;
        this.contactPerson = contactPerson;
        this.maxEnrolledUsers = maxEnrolledUsers;
        this.location = location;
        this.keywords = keywords;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
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

    public List<User> getEnrolledUsers() {

        return enrolledUsers;
    }

    public void setEnrolledUsers(List<User> enrolledUsers) {

        this.enrolledUsers = enrolledUsers;
    }

    public User getContactPerson() {

        return contactPerson;
    }

    public void setContactPerson(User contactPerson) {

        this.contactPerson = contactPerson;
    }

    public Integer getMaxEnrolledUsers() {

        return maxEnrolledUsers;
    }

    public void setMaxEnrolledUsers(Integer maxEnrolledUsers) {

        this.maxEnrolledUsers = maxEnrolledUsers;
    }

    public Location getLocation() {

        return location;
    }

    public void setLocation(Location location) {

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

    public MeetingHall getMeetingHall() {

        return meetingHall;
    }

    public void setMeetingHall(MeetingHall meetingHall) {

        this.meetingHall = meetingHall;
    }
}
