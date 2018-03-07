package dto;

public class ReviewDTO {
    private int idReview;


    private UserDTO user;


    private CourseDTO course;

    private String message;

    private int rating;

    private int pointsFromUsers;

    public int getIdReview() {
        return idReview;
    }

    public void setIdReview(int idReview) {
        this.idReview = idReview;
    }


    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public CourseDTO getCourse() {
        return course;
    }

    public void setCourse(CourseDTO course) {
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
