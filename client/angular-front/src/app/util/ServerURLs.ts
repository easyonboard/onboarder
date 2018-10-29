export class ServerURLs {

  public static ENDPOINT = 'http://localhost:8090/';
  public static ALL_TUTORIALS = ServerURLs.ENDPOINT + 'tutorials';
  public static ALL_EVENTS = ServerURLs.ENDPOINT + 'events';


  public static ALL_USERS = ServerURLs.ENDPOINT + 'users/allUsers';
  public static ALL_MSG_MAILS = ServerURLs.ENDPOINT + 'getAllMsgMails';
  public static ADD_USER = ServerURLs.ENDPOINT + 'users/addUser';
  public static UPDATE_USER = ServerURLs.ENDPOINT + 'users/updateUser';
  public static USER_NAME = ServerURLs.ENDPOINT + '/user?name=';
  public static USER_DEPARTMENT_FOR_USERNAME = ServerURLs.ENDPOINT + 'users/department?msgMail=';
  public static NEW_USERS = ServerURLs.ENDPOINT + 'users/newUsers';
  public static CHECKLIST = ServerURLs.ENDPOINT + 'users/checkList';
  public static SAVE_CHECKLIST = ServerURLs.ENDPOINT + 'users/saveCheckList';
  public static REMOVE_USER = ServerURLs.ENDPOINT + 'users/removeUser';
  public static CHECK_USER_UNICITY = ServerURLs.ENDPOINT + 'users/checkUnicity';
  public static USER_BY_USERNAME = ServerURLs.ENDPOINT + 'username/?username=';
  public static USER_BY_MSG_MAIL = ServerURLs.ENDPOINT + 'msgMail/?msgMail=';
  public static STATUS_MAIL = ServerURLs.ENDPOINT + 'users/isMailSent';
  public static LEAVE_CHECKLIST = ServerURLs.ENDPOINT + 'users/leaveCheckList';
  public static SAVE_LEAVE_CHECKLIST = ServerURLs.ENDPOINT + 'users/saveLeaveCheckList';


  public static ADD_TUTORIAL = ServerURLs.ALL_TUTORIALS + '/add';
  public static ADD_MATERIAL = ServerURLs.ALL_TUTORIALS + '/addMaterial';
  public static FIND_MATERIAL_BY_ID = ServerURLs.ALL_TUTORIALS + '/materialTutorial?id=';
  public static GET_MATERIALS_FOR_TUTORIAL = ServerURLs.ALL_TUTORIALS + '/materialsForTutorial?id=';
  public static SEARCH_TUTORIAL_BY_KEYWORD = ServerURLs.ALL_TUTORIALS + '?keyword=';
  public static SEARCH_TUTORIAL_BY_ID = ServerURLs.ALL_TUTORIALS + '/';
  public static DELETE_TUTORIAL = ServerURLs.ALL_TUTORIALS + '/deleteTutorial';
  public static DELETE_MATERIAL = ServerURLs.ENDPOINT + 'material/delete?id=';


  public static ADD_EVENT = ServerURLs.ALL_EVENTS + '/addEvent';
  public static PAST_EVENT = ServerURLs.ALL_EVENTS + '/pastEvent';
  public static UPCOMING_EVENT = ServerURLs.ALL_EVENTS + '/upcomingEvent';
  public static ENROLL_USER = ServerURLs.ALL_EVENTS + '/enrollUser';
  public static PAST_EVENT_FILTER_BY_KEYWORD = ServerURLs.PAST_EVENT + '?keyword=';
  public static UPCOMING_EVENT_FILTER_BY_KEYWORD = ServerURLs.UPCOMING_EVENT + '?keyword=';
  public static UNENROLL_USER = ServerURLs.ALL_EVENTS + '/unenrollUser';
  public static SERVER_IS_ENROLLED = ServerURLs.ALL_EVENTS + '/isEnrolled';
  public static SERVER_DELETE_UPCOMING_EVENT = ServerURLs.ENDPOINT + '/deleteEvent/upcoming';
  public static SERVER_DELETE_PAST_EVENT = ServerURLs.ENDPOINT + '/deleteEvent/past';

  public static ALL_LOCATIONS = ServerURLs.ENDPOINT + 'locations';
  public static ALL_ROOMS = ServerURLs.ENDPOINT + 'meetingHalls';
  public static ALL_DEPARTMENTS = ServerURLs.ENDPOINT + 'departments';


  public static GENERATE_TOKEN_URL = 'http://localhost:8090/token/generate-token';
}
