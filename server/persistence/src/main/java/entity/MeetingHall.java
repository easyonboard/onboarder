package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
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

}
