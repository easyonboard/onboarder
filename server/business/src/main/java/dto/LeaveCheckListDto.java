package dto;

public class LeaveCheckListDto {

    private Integer idCheckList;
    private UserDto userAccount;
    private boolean inventoryObjects;
    private boolean resignationForm;
    private boolean cards;


    public Integer getIdCheckList() {
        return idCheckList;
    }

    public void setIdCheckList(Integer idCheckList) {
        this.idCheckList = idCheckList;
    }

    public UserDto getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserDto userAccount) {
        this.userAccount = userAccount;
    }

    public boolean isInventoryObjects() {
        return inventoryObjects;
    }

    public void setInventoryObjects(boolean inventoryObjects) {
        this.inventoryObjects = inventoryObjects;
    }

    public boolean isResignationForm() {
        return resignationForm;
    }

    public void setResignationForm(boolean resignationForm) {
        this.resignationForm = resignationForm;
    }

    public boolean isCards() {
        return cards;
    }

    public void setCards(boolean cards) {
        this.cards = cards;
    }
}
