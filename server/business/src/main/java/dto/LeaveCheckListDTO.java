package dto;

public class LeaveCheckListDTO {  private Integer idCheckList;

    private UserDTO userAccount;
    private boolean inventoryObjects;
    private boolean resignationForm;
    private boolean cards;

    public Integer getIdCheckList() {
        return idCheckList;
    }

    public void setIdCheckList(Integer idCheckList) {
        this.idCheckList = idCheckList;
    }

    public UserDTO getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserDTO userAccount) {
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
