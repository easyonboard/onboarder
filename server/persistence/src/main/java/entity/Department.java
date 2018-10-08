package entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int idDepartment;

    @Column
    @NotNull
    private String departmentName;

    @ManyToOne
    private Department parent;

    @JsonBackReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Department> childDepartments;

}
