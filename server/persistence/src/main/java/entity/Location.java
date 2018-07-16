package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import entity.enums.LocationName;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NamedQueries({@NamedQuery(name = Location.FIND_LOCATION_BY_NAME, query = "select l from Location l where l.locationName=:name")})
@Entity
public class Location implements Serializable {

    public static final String FIND_LOCATION_BY_NAME = "Location.findLocationByName";

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


    @NotNull
    @Column
    private String locationCity;

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

    public String getLocationCity() {
        return locationCity;
    }

    public void setLocationCity(String locationCity) {
        this.locationCity = locationCity;
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
