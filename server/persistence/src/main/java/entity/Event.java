package entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
@Entity
public class Event implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idEvent;

    @Column
    private String titleEvent;

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
    @Column
    private String otherLocation;

    @ManyToOne
    @JoinColumn(name = "idMeetingHall")
    private MeetingHall meetingHall;

    @Column
    private String keywords;

    @Column
    private Date eventDate;

    @Column
    private String eventTime;
}
