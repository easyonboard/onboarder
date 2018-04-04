package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import entity.User;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CheckListDTO {

    private Integer idCheckList;
    private User userAccount;

    private boolean initialPassword;

    private boolean laptopOrder;

    private boolean hasBuddyAssigned;

    private boolean mailSentToBuddy;

    private boolean mailSent;

    private boolean isUserInOVA;

    private boolean hasGermanCourseAssigned;

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

    public void setIsUserInOVA(boolean isUserInOVA) {
        this.isUserInOVA = isUserInOVA;
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

    public void setIsAddedToVerteiler(boolean isAddedToVerteiler) {
        this.isAddedToVerteiler = isAddedToVerteiler;
    }
}
