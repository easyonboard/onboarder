package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class CheckList implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCheckList;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account")
    private User userAccount;

    @Column
    private boolean initialPassword;

    @Column
    private boolean laptopOrder;

    @Column
    private boolean hasBuddyAssigned;

    @Column
    private boolean mailSentToBuddy;

    @Column
    private boolean mailSent;

    @Column
    private boolean isUserInOVA;

    @Column
    private boolean hasGermanCourseAssigned;

    @Column
    private boolean isAddedToVerteiler;
}
