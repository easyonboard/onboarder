package entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idDepartment;

    @Column
    @NotNull
    private String departmentName;

    @OneToMany
    @JoinColumn(name = "idDepartment")
    private List<Department> childDepartments;
}
