package entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Review implements Serializable {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private int idReview;

    @ManyToOne
    private User user;

    @ManyToOne
    private Course course;

    private String message;

    private int rating;

    private int pointsFromUsers;


    public int getIdReview() {
        return idReview;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getPointsFromUsers() {
        return pointsFromUsers;
    }

    public void setPointsFromUsers(int pointsFromUsers) {
        this.pointsFromUsers = pointsFromUsers;
    }
}
