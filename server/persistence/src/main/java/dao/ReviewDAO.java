package dao;

import entity.Review;
import org.springframework.stereotype.Repository;

@Repository
public class ReviewDAO extends AbstractDAO<Review> {
    @Override
    public Class<Review> getEntityClass() {
        return Review.class;
    }
}
