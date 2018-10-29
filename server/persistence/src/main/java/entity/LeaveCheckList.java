package entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
public class LeaveCheckList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idCheckList;


    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_account")
    private User userAccount;

//    @Column(columnDefinition = "NUMERIC(1)")
    private boolean inventoryObjects;

//    @Column(columnDefinition = "NUMERIC(1)")
    private boolean resignationForm;
//    @Column(columnDefinition = "NUMERIC(1)")
    private boolean cards;
}
