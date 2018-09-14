package entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int idDepartment;

    @Column
    @NotNull
    private String departmentName;

    @OneToMany
    @JoinColumn(name="idDepartment")
    private List<Department> childDepartments;

    public int getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(int idDepartment) {
        this.idDepartment = idDepartment;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public List<Department> getChildDepartments() {
        return childDepartments;
    }

    public void setChildDepartments(List<Department> childDepartments) {
        this.childDepartments = childDepartments;
    }
}
