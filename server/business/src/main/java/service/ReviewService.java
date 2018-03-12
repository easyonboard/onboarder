package service;

import dao.CourseDAO;
import dao.ReviewDAO;
import dao.UserDAO;
import dto.CourseDTO;
import dto.ReviewDTO;
import dto.mapper.ReviewMapper;
import entity.Course;
import entity.Review;
import entity.User;
import exception.CourseNotFoundException;
import exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private ReviewMapper reviewMapper = ReviewMapper.INSTANCE;
    private static final String USER_NOT_FOUND_ERROR = "User not found";
    private static final String COURSE_NOT_FOUND_ERROR = "User not found";

    @Autowired
    private ReviewDAO reviewDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private CourseDAO courseDAO;

    public void addReview(ReviewDTO review) throws UserNotFoundException, CourseNotFoundException {
        Review reviewEntity = new Review();
        reviewMapper.mapToEntity(review, reviewEntity);
        Optional<User> optionalUser = userDAO.findUserByUsername(review.getUser().getUsername());
        User userEntity;
        if (optionalUser.isPresent())
            userEntity = optionalUser.get();
        else throw new UserNotFoundException(USER_NOT_FOUND_ERROR);
        Course courseEntity = courseDAO.findEntity(review.getCourse().getIdCourse());
        if(courseEntity==null)
            throw new CourseNotFoundException(COURSE_NOT_FOUND_ERROR);
        reviewEntity.setCourse(courseEntity);
        reviewEntity.setUser(userEntity);
        reviewDAO.persistEntity(reviewEntity);


    }

    public List<ReviewDTO> getAllReviews(CourseDTO course) {

        return reviewMapper.entitiesToDTOs(reviewDAO.findReviewsByCourse(courseDAO.findEntity(course.getIdCourse())));

    }

    public Review voteUp(ReviewDTO review) {
        Review reviewEntity=reviewDAO.findEntity(review.getIdReview());
        reviewEntity.setPointsFromUsers(reviewEntity.getPointsFromUsers()+1);
        reviewDAO.persistEntity(reviewEntity);
        return reviewEntity;
    }
}
