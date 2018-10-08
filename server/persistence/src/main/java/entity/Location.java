package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.enums.LocationName;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
public class Location implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idLocation;

    @NotNull
    @Column
    @Enumerated(EnumType.STRING)
    private LocationName locationName;

    @NotNull
    @Column
    private String locationAddress;

    @Column
    private String locationContactPhone;

    @Column
    private String locationContactEmail;

    @JsonBackReference(value = "event-list")
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Event> events;
}
