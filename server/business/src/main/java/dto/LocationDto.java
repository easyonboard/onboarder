package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.enums.LocationName;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationDto {


    private Integer idLocation;

    private LocationName locationName;

    private String locationAddress;

    private String locationContactPhone;

    private String locationContactEmail;
}
