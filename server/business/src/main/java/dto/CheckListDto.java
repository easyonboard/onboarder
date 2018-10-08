package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckListDto {

    private Integer idCheckList;
    private UserDto userAccount;

    private boolean initialPassword;

    private boolean laptopOrder;

    private boolean hasBuddyAssigned;

    private boolean mailSentToBuddy;

    private boolean mailSent;

    private boolean isUserInOVA;

    private boolean hasGermanCourseAssigned;

    private boolean isAddedToVerteiler;
}
