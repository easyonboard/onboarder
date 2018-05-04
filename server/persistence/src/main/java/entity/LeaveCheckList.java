package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class LeaveCheckList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCheckList;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account")
    private User userAccount;

    @Column(columnDefinition="NUMBER(1)")
    private boolean inventoryObjects;

    @Column(columnDefinition="NUMBER(1)")
    private boolean resignationForm;
    @Column(columnDefinition="NUMBER(1)")
    private boolean cards;


    public void setIdCheckList(Integer idCheckList) {
        this.idCheckList = idCheckList;
    }

    public Integer getIdCheckList() {
        return idCheckList;
    }

    public User getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(User userAccount) {
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
