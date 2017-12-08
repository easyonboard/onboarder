export class RootConst{
  public WEB_SERVICE_ENDPOINT = 'http://localhost:8090/';
  public SERVER_COURSES_URL = this.WEB_SERVICE_ENDPOINT  + 'courses';
  public SERVER_COURSE_OVERVIEW = this.WEB_SERVICE_ENDPOINT+'course?overview=';
  public SERVER_DETAILED_COURSE = this.WEB_SERVICE_ENDPOINT+'detailedCourse?id=';


  public FRONT_DETAILED_COURSE = '/detailedCourse';
  public FRONT_OVERVIEW_SECTION='overview';
  public FRONT_SYLLABUS_SECTION='syllabus';
  public FRONT_OWNER_SECTION='owner';
  public FRONT_CONTACT_PERSON_SECTION='contactPerson';
  public FRONT_RATINGS_REVIEW_SECTION ='retingsReview';

}
