package dto;

import entity.enums.LocationName;

public class LocationDto {


    private Integer idLocation;

    private LocationName locationName;

    private String locationAddress;

    private String locationCity;

    private String locationContactPhone;

    private String locationContactEmail;

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

}
