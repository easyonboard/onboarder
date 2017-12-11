package dao;

import entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDAO extends AbstractDAO<Course>{
    @Override
    public Class<Course> getEntityClass() {
        return Course.class;
    }
}
