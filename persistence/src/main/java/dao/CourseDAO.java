package dao;

import entity.Course;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class CourseDAO extends AbstractDAO<Course>{
    @Override
    public Class<Course> getEntityClass() {
        return Course.class;
    }

    public List<Course> allCourses() {
        CriteriaBuilder cb=  this.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);
        Root<Course> rootUser = criteriaQuery.from(Course.class);

        criteriaQuery.select(rootUser);
        return (List<Course>) this.executeCriteriaQuery(criteriaQuery);
    }

    public List<Course> searchByOverview(String overview) {
        CriteriaBuilder cb=  this.getCriteriaBuilder();
        CriteriaQuery<Course> criteriaQuery = cb.createQuery(Course.class);
        Root<Course> rootCourse = criteriaQuery.from(Course.class);

        criteriaQuery.select(cb.construct(Course.class,
                rootCourse.get("idCourse"),rootCourse.get("titleCourse"),rootCourse.get("overview"))).where(cb.like(rootCourse.<String>get("overview"),"%"+overview+"%"));

        List<Course> courses = this.executeCriteriaQuery(criteriaQuery);
        return courses ;
    }
}
