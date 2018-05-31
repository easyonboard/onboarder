package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class CheckList implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCheckList;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account")
    private User userAccount;

    @Column(columnDefinition="NUMBER(1)")
    private boolean initialPassword;

    @Column(columnDefinition="NUMBER(1)")
    private boolean laptopOrder;

    @Column(columnDefinition="NUMBER(1)")
    private boolean hasBuddyAssigned;

    @Column(columnDefinition="NUMBER(1)")
    private boolean mailSentToBuddy;

    @Column(columnDefinition="NUMBER(1)")
    private boolean mailSent;

    @Column(columnDefinition="NUMBER(1)")
    private boolean isUserInOVA;

    @Column(columnDefinition="NUMBER(1)")
    private boolean hasGermanCourseAssigned;

    @Column(columnDefinition="NUMBER(1)")
    private boolean isAddedToVerteiler;

    public Integer getIdCheckList() {
        return idCheckList;
    }

    public void setIdCheckList(Integer idCheckList) {
        this.idCheckList = idCheckList;
    }

    public User getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(User userAccount) {
        this.userAccount = userAccount;
    }

    public boolean isInitialPassword() {
        return initialPassword;
    }

    public void setInitialPassword(boolean initialPassword) {
        this.initialPassword = initialPassword;
    }

    public boolean isLaptopOrder() {
        return laptopOrder;
    }

    public void setLaptopOrder(boolean laptopOrder) {
        this.laptopOrder = laptopOrder;
    }

    public boolean isHasBuddyAssigned() {
        return hasBuddyAssigned;
    }

    public void setHasBuddyAssigned(boolean hasBuddyAssigned) {
        this.hasBuddyAssigned = hasBuddyAssigned;
    }

    public boolean isMailSentToBuddy() {
        return mailSentToBuddy;
    }

    public void setMailSentToBuddy(boolean mailSentToBuddy) {
        this.mailSentToBuddy = mailSentToBuddy;
    }

    public boolean isMailSent() {
        return mailSent;
    }

    public void setMailSent(boolean mailSent) {
        this.mailSent = mailSent;
    }

    public boolean isUserInOVA() {
        return isUserInOVA;
    }

    public void setUserInOVA(boolean userInOVA) {
        isUserInOVA = userInOVA;
    }

    public boolean isHasGermanCourseAssigned() {
        return hasGermanCourseAssigned;
    }

    public void setHasGermanCourseAssigned(boolean hasGermanCourseAssigned) {
        this.hasGermanCourseAssigned = hasGermanCourseAssigned;
    }

    public boolean isAddedToVerteiler() {
        return isAddedToVerteiler;
    }

    public void setAddedToVerteiler(boolean addedToVerteiler) {
        isAddedToVerteiler = addedToVerteiler;
    }
}
