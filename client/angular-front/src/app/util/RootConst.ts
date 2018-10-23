export class RootConst {

  public SERVER_ENDPOINT = 'http://localhost:8090/';
  public SERVER_TUTORIALS_URL = this.SERVER_ENDPOINT + 'tutorials';
  public SERVER_EVENTS_URL = this.SERVER_ENDPOINT + 'events';


  public SERVER_ALL_USERS = this.SERVER_ENDPOINT + 'users/allUsers';
  public SERVER_ALL_MSG_MAILS = this.SERVER_ENDPOINT + 'getAllMsgMails';
  public SERVER_ADD_USER = this.SERVER_ENDPOINT + 'users/addUser';
  public SERVER_UPDATE_USER = this.SERVER_ENDPOINT + 'users/updateUser';
  public SERVER_USER_NAME = this.SERVER_ENDPOINT + '/user?name=';
  public SERVER_LOGGED_USER_DEPARTMENT = this.SERVER_ENDPOINT + 'users/department?msgMail=';
  public SERVER_NEWUSERS = this.SERVER_ENDPOINT + 'users/newUsers';
  public SERVER_CHECKLIST = this.SERVER_ENDPOINT + 'users/checkList';
  public SERVER_SAVE_CHECKLIST = this.SERVER_ENDPOINT + 'users/saveCheckList';
  public SERVER_REMOVE_USER = this.SERVER_ENDPOINT + 'users/removeUser';
  public SERVER_CHECK_USER_UNICITY = this.SERVER_ENDPOINT + 'users/checkUnicity';
  public SERVER_USER_BY_USERNAME = this.SERVER_ENDPOINT + 'username/?username=';
  public SERVER_STATUS_MAIL = this.SERVER_ENDPOINT + 'users/isMailSent';
  public SERVER_LEAVE_CHECKLIST = this.SERVER_ENDPOINT + 'users/leaveCheckList';
  public SERVER_SAVE_LEAVE_CHECKLIST = this.SERVER_ENDPOINT + 'users/saveLeaveCheckList';


  public SERVER_ADD_TUTORIAL = this.SERVER_TUTORIALS_URL + '/add';
  public SERVER_ADD_TUTORIAL_MATERIAL = this.SERVER_TUTORIALS_URL + '/addMaterial';
  public SERVER_FIND_TUTORIAL_MATERIAL_BY_ID = this.SERVER_TUTORIALS_URL + '/materialTutorial?id=';
  public SERVER_GET_MATERIALS_FOR_TUTORIAL = this.SERVER_TUTORIALS_URL + '/materialsForTutorial?id=';
  public SERVER_SEARCH_TUTORIAL_BY_KEYWORD = this.SERVER_TUTORIALS_URL + '?keyword=';
  public SERVER_SEARCH_TUTORIAL_BY_ID = this.SERVER_TUTORIALS_URL + '/';
  public SERVER_DELETE_TUTORIAL = this.SERVER_TUTORIALS_URL + '/deleteTutorial';
  public SERVER_DELETE_MATERIAL = this.SERVER_ENDPOINT + 'material/delete?id=';


  public SERVER_ADD_EVENT = this.SERVER_EVENTS_URL + '/addEvent';
  public SERVER_PAST_EVENT = this.SERVER_EVENTS_URL + '/pastEvent';
  public SERVER_UPCOMING_EVENT = this.SERVER_EVENTS_URL + '/upcomingEvent';
  public SERVER_ENROLL_USER = this.SERVER_EVENTS_URL + '/enrollUser';
  public SERVER_PAST_EVENT_FILTER_BY_KEYWORD = this.SERVER_PAST_EVENT + '?keyword=';
  public SERVER_UPCOMING_EVENT_FILTER_BY_KEYWORD = this.SERVER_UPCOMING_EVENT + '?keyword=';
  public SERVER_UNENROLL_USER = this.SERVER_EVENTS_URL + '/unenrollUser';
  public SERVER_IS_ENROLLED = this.SERVER_EVENTS_URL + '/isEnrolled';


  public SERVER_LOCATIONS = this.SERVER_ENDPOINT + 'locations';
  public SERVER_ROOMS = this.SERVER_ENDPOINT + 'meetingHalls';
  public SERVER_DEPARTMENTS = this.SERVER_ENDPOINT + 'departments';


  public GENERATE_TOKEN_URL = 'http://localhost:8090/token/generate-token';
  public FRONT_TUTORIALS_PAGE = '/tutorials';
  public FRONT_LOGIN_PAGE = '/login';
  public FRONT_INFOS_PAGE = '/info';
  public FRONT_EVENTS_PAGE = 'events/viewEvents';


}
