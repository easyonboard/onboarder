package entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idDepartment;

    @Column
    @NotNull
    private String departmentName;

    @OneToMany
    @JoinColumn(name = "idDepartment")
    private List<Department> childDepartments;

    public Department(){}

}
