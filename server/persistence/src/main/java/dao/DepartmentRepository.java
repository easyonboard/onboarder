package dao;

import entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    Department findByDepartmentName(String departmentName);
    List<Department> findByParent(Department department);
}
