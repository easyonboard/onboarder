package dao;

import entity.Course;
import entity.Review;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

@Repository
public class ReviewDAO extends AbstractDAO<Review> {
    @Override
    public Class<Review> getEntityClass() {
        return Review.class;
    }

    public List<Review> findReviewsByCourse(Course courseEntity) {
        Query query=em.createQuery("select r from Review r where r.course=:course");
        query.setParameter("course", courseEntity);
        return query.getResultList();
    }
}
