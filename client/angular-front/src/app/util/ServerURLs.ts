export class ServerURLs {

  public static SERVER_ENDPOINT = 'http://localhost:8090/';
  public static SERVER_TUTORIALS_URL = ServerURLs.SERVER_ENDPOINT + 'tutorials';
  public static SERVER_EVENTS_URL = ServerURLs.SERVER_ENDPOINT + 'events';


  public static SERVER_ALL_USERS = ServerURLs.SERVER_ENDPOINT + 'users/allUsers';
  public static SERVER_ALL_MSG_MAILS = ServerURLs.SERVER_ENDPOINT + 'getAllMsgMails';
  public static SERVER_ADD_USER = ServerURLs.SERVER_ENDPOINT + 'users/addUser';
  public static SERVER_UPDATE_USER = ServerURLs.SERVER_ENDPOINT + 'users/updateUser';
  public static SERVER_USER_NAME = ServerURLs.SERVER_ENDPOINT + '/user?name=';
  public static SERVER_LOGGED_USER_DEPARTMENT = ServerURLs.SERVER_ENDPOINT + 'users/department?username=';
  public static SERVER_NEWUSERS = ServerURLs.SERVER_ENDPOINT + 'users/newUsers';
  public static SERVER_CHECKLIST = ServerURLs.SERVER_ENDPOINT + 'users/checkList';
  public static SERVER_SAVE_CHECKLIST = ServerURLs.SERVER_ENDPOINT + 'users/saveCheckList';
  public static SERVER_REMOVE_USER = ServerURLs.SERVER_ENDPOINT + 'users/removeUser';
  public static SERVER_CHECK_USER_UNICITY = ServerURLs.SERVER_ENDPOINT + 'users/checkUnicity';
  public static SERVER_USER_BY_USERNAME = ServerURLs.SERVER_ENDPOINT + 'username/?username=';
  public static SERVER_STATUS_MAIL = ServerURLs.SERVER_ENDPOINT + 'users/isMailSent';
  public static SERVER_LEAVE_CHECKLIST = ServerURLs.SERVER_ENDPOINT + 'users/leaveCheckList';
  public static SERVER_SAVE_LEAVE_CHECKLIST = ServerURLs.SERVER_ENDPOINT + 'users/saveLeaveCheckList';


  public static SERVER_ADD_TUTORIAL = ServerURLs.SERVER_TUTORIALS_URL + '/add';
  public static SERVER_ADD_TUTORIAL_MATERIAL = ServerURLs.SERVER_TUTORIALS_URL + '/addMaterial';
  public static SERVER_FIND_TUTORIAL_MATERIAL_BY_ID = ServerURLs.SERVER_TUTORIALS_URL + '/materialTutorial?id=';
  public static SERVER_GET_MATERIALS_FOR_TUTORIAL = ServerURLs.SERVER_TUTORIALS_URL + '/materialsForTutorial?id=';
  public static SERVER_SEARCH_TUTORIAL_BY_KEYWORD = ServerURLs.SERVER_TUTORIALS_URL + '?keyword=';
  public static SERVER_SEARCH_TUTORIAL_BY_ID = ServerURLs.SERVER_TUTORIALS_URL + '/';
  public static SERVER_DELETE_TUTORIAL = ServerURLs.SERVER_TUTORIALS_URL + '/deleteTutorial';
  public static SERVER_DELETE_MATERIAL = ServerURLs.SERVER_ENDPOINT + 'material/delete?id=';


  public static SERVER_ADD_EVENT = ServerURLs.SERVER_EVENTS_URL + '/addEvent';
  public static SERVER_PAST_EVENT = ServerURLs.SERVER_EVENTS_URL + '/pastEvent';
  public static SERVER_UPCOMING_EVENT = ServerURLs.SERVER_EVENTS_URL + '/upcomingEvent';
  public static SERVER_ENROLL_USER = ServerURLs.SERVER_EVENTS_URL + '/enrollUser';
  public static SERVER_PAST_EVENT_FILTER_BY_KEYWORD = ServerURLs.SERVER_PAST_EVENT + '?keyword=';
  public static SERVER_UPCOMING_EVENT_FILTER_BY_KEYWORD = ServerURLs.SERVER_UPCOMING_EVENT + '?keyword=';
  public static SERVER_UNENROLL_USER = ServerURLs.SERVER_EVENTS_URL + '/unenrollUser';
  public static SERVER_IS_ENROLLED = ServerURLs.SERVER_EVENTS_URL + '/isEnrolled';


  public static SERVER_LOCATIONS = ServerURLs.SERVER_ENDPOINT + 'locations';
  public static SERVER_ROOMS = ServerURLs.SERVER_ENDPOINT + 'meetingHalls';
  public static SERVER_DEPARTMENTS = ServerURLs.SERVER_ENDPOINT + 'departments';


}
