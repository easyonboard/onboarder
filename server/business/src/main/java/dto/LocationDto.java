package dto;

import entity.enums.LocationName;
import lombok.Data;

@Data
public class LocationDto {


    private Integer idLocation;

    private LocationName locationName;

    private String locationAddress;

    private String locationCity;

    private String locationContactPhone;

    private String locationContactEmail;
}
