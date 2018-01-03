export class RootConst{
  public WEB_SERVICE_ENDPOINT = 'http://localhost:8090/';
  public SERVER_COURSES_URL = this.WEB_SERVICE_ENDPOINT  + 'courses';
  public REDIRECT_LOGIN_SUCCESS_URL='courses';
  public SERVER_COURSE_OVERVIEW = this.SERVER_COURSES_URL+'/course?overview=';
  public SERVER_DETAILED_COURSE = this.SERVER_COURSES_URL+'/detailedCourse?id=';
  public SERVER_TEST_IF_USER_IS_ENROLLED=this.SERVER_COURSES_URL+'/isEnrolledOnCourse?idCourse=';
  public SERVER_ENROLLE_USER_ON_COURSE= this.SERVER_COURSES_URL+'/enrollUserOnCourse?idCourse='
  public SERVER_UNENROLLE_USER_ON_COURSE=this.SERVER_COURSES_URL+'/unenrollUserFromCourse?idCourse=' ;
  public SERVER_ADD_USER=this.WEB_SERVICE_ENDPOINT+'user/addUser';
  public SERVER_UPDATE_USER=this.WEB_SERVICE_ENDPOINT + "/user/updateUser";
  public SERVER_AUTHENTIFICATION=this.WEB_SERVICE_ENDPOINT + "/auth";



  public FRONT_DETAILED_COURSE = '/courses/detailedCourse';
  public FRONT_OVERVIEW_SECTION='overview';
  public FRONT_SYLLABUS_SECTION='syllabus';
  public FRONT_OWNER_SECTION='owner';
  public FRONT_CONTACT_PERSON_SECTION='contactPerson';
  public FRONT_RATINGS_REVIEW_SECTION ='ratingsReview';
  public LOGIN_PAGE = '';

}
