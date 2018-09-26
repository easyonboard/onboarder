package dto;

import lombok.Data;

@Data
public class LeaveCheckListDto {

    private Integer idCheckList;
    private UserDto userAccount;
    private boolean inventoryObjects;
    private boolean resignationForm;
    private boolean cards;
}
