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

    @Column(columnDefinition="NUMERIC(1)")
    private boolean initialPassword;

    @Column(columnDefinition="NUMERIC(1)")
    private boolean laptopOrder;

    @Column(columnDefinition="NUMERIC(1)")
    private boolean hasBuddyAssigned;

    @Column(columnDefinition="NUMERIC(1)")
    private boolean mailSentToBuddy;

    @Column(columnDefinition="NUMERIC(1)")
    private boolean mailSent;

    @Column(columnDefinition="NUMERIC(1)")
    private boolean isUserInOVA;

    @Column(columnDefinition="NUMERIC(1)")
    private boolean hasGermanCourseAssigned;

    @Column(columnDefinition="NUMERIC(1)")
    private boolean isAddedToVerteiler;
}
