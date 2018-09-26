package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.enums.LocationName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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

    public Integer getIdLocation() {

        return idLocation;
    }

    public void setIdLocation(Integer idLocation) {

        this.idLocation = idLocation;
    }

    public LocationName getLocationName() {

        return locationName;
    }

    public void setLocationName(LocationName locationName) {

        this.locationName = locationName;
    }

    public String getLocationAddress() {

        return locationAddress;
    }

    public void setLocationAddress(String locationAddress) {

        this.locationAddress = locationAddress;
    }


    public String getLocationContactPhone() {

        return locationContactPhone;
    }

    public void setLocationContactPhone(String locationContactPhone) {

        this.locationContactPhone = locationContactPhone;
    }

    public String getLocationContactEmail() {

        return locationContactEmail;
    }

    public void setLocationContactEmail(String locationContactEmail) {

        this.locationContactEmail = locationContactEmail;
    }

    public List<Event> getEvents() {

        return events;
    }

    public void setEvents(List<Event> events) {

        this.events = events;
    }
}
