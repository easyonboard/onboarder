export class RootConst {
  public WEB_SERVICE_ENDPOINT = 'http://localhost:8090/';


  public SERVER_COURSES_URL = this.WEB_SERVICE_ENDPOINT + 'courses';
  public SERVER_COURSE_OVERVIEW = this.SERVER_COURSES_URL + '/course?overview=';
  public SERVER_FILTER_COURSES_BY_KEYWORD = this.WEB_SERVICE_ENDPOINT + '/courses?keyword=';


  public SERVER_DETAILED_COURSE = this.SERVER_COURSES_URL + '/detailedCourse?id=';
  public SERVER_COURSES_BY_PAGE_NUMBER_URL = this.SERVER_COURSES_URL + 'FromPage/?pageNumber=';
  public SERVER_COURSES_NUMBER_OF_OBJECT_PER_PAGE = '&numberOfObjectsPerPage=';


  public SERVER_TEST_IF_USER_IS_ENROLLED = this.SERVER_COURSES_URL + '/isEnrolledOnCourse?idCourse=';
  public SERVER_ENROLLE_USER_ON_COURSE = this.SERVER_COURSES_URL + '/enrollUserOnCourse?idCourse=';
  public SERVER_ALL_USERS = this.WEB_SERVICE_ENDPOINT + 'allUsers';
  public SERVER_UNENROLLE_USER_ON_COURSE = this.SERVER_COURSES_URL + '/unenrollUserFromCourse?idCourse=';
  public SERVER_ADD_USER = this.WEB_SERVICE_ENDPOINT + 'user/addUser';
  public SERVER_UPDATE_USER = this.WEB_SERVICE_ENDPOINT + '/user/updateUser';
  public SERVER_USER_COURSES = this.WEB_SERVICE_ENDPOINT + '/user/courses?username=';
  public SERVER_USER_USERNAME = this.WEB_SERVICE_ENDPOINT + '/user/'
  public VOTEUP = this.WEB_SERVICE_ENDPOINT + 'voteUp';


  public SERVER_AUTHENTIFICATION = this.WEB_SERVICE_ENDPOINT + '/auth';
  public SERVER_DELETE_CONTACT_PERSON = this.WEB_SERVICE_ENDPOINT + 'deleteContactPerson';
  public SERVER_DELETE_OWNER_PERSON = this.WEB_SERVICE_ENDPOINT + 'deleteOwnerPerson';
  public SERVER_ADD_CONTACT_PERSON = this.WEB_SERVICE_ENDPOINT + 'addContactPerson';
  public SERVER_DELETE_COURSE = this.WEB_SERVICE_ENDPOINT + 'courses/deleteCourse';
  public SERVER_ADD_OWNER_PERSON = this.WEB_SERVICE_ENDPOINT + 'addOwnerPerson';
  public SERVER_SUBJECT_URL2 = '&idSubject=';
  public SERVER_SUBJECT_URL = this.WEB_SERVICE_ENDPOINT + 'courses/subject?id=';
  public SERVER_ADD_COURSE = this.SERVER_COURSES_URL + '/addCourse';

  public SERVER_DELETE_SUBJECT = this.WEB_SERVICE_ENDPOINT + 'deleteCourseSubject';
  public WEB_SERVER_PROGRESS = this.WEB_SERVICE_ENDPOINT + 'progress';
  public WEB_SERVICE_MARK_AS_FINISHED = this.WEB_SERVICE_ENDPOINT + 'markAsFinished';
  public SERVER_ADD_SUBJECT = this.WEB_SERVICE_ENDPOINT + 'subject/addSubject';
  public SERVER_FIND_MATERIAL_BY_ID = this.SERVER_COURSES_URL + '/material?id=';
  public SERVER_MATERIALS_UPLOADED_BY_USE = this.WEB_SERVICE_ENDPOINT + '/material/uploadedMaterial?username=';
  public WEB_SERVICE_SUBJECT_STATUS = this.WEB_SERVICE_ENDPOINT + 'subjectStatus';

  public WEB_SERVER_GET_STATUS = this.WEB_SERVICE_ENDPOINT + 'isSubjectFinished';
  public SERVER_MATERIALS_FROM_SUBJECT = this.WEB_SERVICE_ENDPOINT + 'materials?idSubject=';
  public SERVER_ADD_MATERIAL = this.WEB_SERVICE_ENDPOINT + 'createMaterial';
  public WEB_SERVICE_RATING = this.WEB_SERVICE_ENDPOINT + 'generalRating';
  public WEB_SERVICE_ADD_REVIEW = this.WEB_SERVICE_ENDPOINT + 'course/addReview';
  public WEB_SERVICE_ALL_REVIEWS = this.WEB_SERVICE_ENDPOINT + 'allReviews';

  public FRONT_COURSES_PAGE = '/courses';
  public FRONT_ADD_COURSE = this.FRONT_COURSES_PAGE + '/addCourse';
  public FRONT_COURSES_PAGE_SEARCH_BY_KEYWORD = '/courses?keyword=';
  public FRONT_DETAILED_COURSE = this.FRONT_COURSES_PAGE + '/detailedCourse';
  public FRONT_DETAILED_SUBJECT = '/subject';
  public FRONT_OVERVIEW_SECTION = 'overview';
  public FRONT_SYLLABUS_SECTION = 'syllabus';
  public FRONT_OWNER_SECTION = 'owner';
  public FRONT_CONTACT_PERSON_SECTION = 'contactPerson';
  public FRONT_RATINGS_REVIEW_SECTION = 'ratingsReview';
  public FRONT_LOGIN_PAGE = '';
  public FRONT_INFOS_PAGE='/infos';

  public SERVER_ADD_USER_INFO_URL = this.WEB_SERVICE_ENDPOINT + '/info';
}
