export class RootConst {
  public WEB_SERVICE_ENDPOINT = 'http://localhost:8090/';

  public SERVER_TUTORIALS_URL = this.WEB_SERVICE_ENDPOINT + 'tutorials';
  public SERVER_EVENTS_URL = this.WEB_SERVICE_ENDPOINT + 'events';

  public SERVER_ALL_USERS = this.WEB_SERVICE_ENDPOINT + 'allUsers';
  public SERVER_ALL_USERS_NAME_EMAIL = this.WEB_SERVICE_ENDPOINT + 'getAllMsgMails';
  public SERVER_ADD_USER = this.WEB_SERVICE_ENDPOINT + 'user/addUser';
  public SERVER_UPDATE_USER_INFO = this.WEB_SERVICE_ENDPOINT + '/user/updateUserInfo';
  public SERVER_UPDATE_USER_PASSWORD = this.WEB_SERVICE_ENDPOINT + '/user/updateUserPassword';
  public SERVER_USER_NAME = this.WEB_SERVICE_ENDPOINT + '/user?name=';
  public SERVER_LOGGED_USER_DEPARTMENT = this.WEB_SERVICE_ENDPOINT + 'user/department?username=';

  public SERVER_AUTHENTIFICATION = this.WEB_SERVICE_ENDPOINT + '/auth';
  public SERVER_ADD_TUTORIAL = this.SERVER_TUTORIALS_URL + '/add';
  public SERVER_ADD_EVENT = this.SERVER_EVENTS_URL + '/addEvent';

  public SERVER_PAST_EVENT = this.SERVER_EVENTS_URL + '/pastEvent';
  public SERVER_UPCOMING_EVENT = this.SERVER_EVENTS_URL + '/upcomingEvent';
  SERVER_ENROLL_USER = this.SERVER_EVENTS_URL + '/enrollUser';
  public WEB_SERVER_NEWUSERS = this.WEB_SERVICE_ENDPOINT + 'newUsers';
  public WEB_SERVER_CHECKLIST = this.WEB_SERVICE_ENDPOINT + 'checkList';
  public WEB_SERVER_SAVE_CHECKLIST = this.WEB_SERVICE_ENDPOINT + 'saveCheckList';

  public FRONT_TUTORIALS_PAGE = '/tutorials';
  public FRONT_ADD_TUTORIAL = this.FRONT_TUTORIALS_PAGE + '/addTutorialRouterLink';
  public FRONT_LOGIN_PAGE = '';

  public FRONT_INFOS_PAGE = '/info';
  public FRONT_EVENTS_PAGE = 'events/viewEvents';


  public REMOVE_USER = this.WEB_SERVICE_ENDPOINT + 'user/removeUser';
  // ***********************************************
  public CHECK_USER_UNICITY = this.WEB_SERVICE_ENDPOINT + 'user/checkUnicity';
  // ***********************************************
  WEB_SERVER_USERINFORMATION = this.WEB_SERVICE_ENDPOINT + 'getUserInformation';
  WEB_SERVER_STATUS_MAIL = this.WEB_SERVICE_ENDPOINT + 'isMailSent';
  WEB_SERVER_LEAVE_CHECKLIST = this.WEB_SERVICE_ENDPOINT + 'leaveCheckList';
  WEB_SERVER_SAVE_LEAVE_CHECKLIST = this.WEB_SERVICE_ENDPOINT + 'saveLeaveCheckList';
  WEB_SERVER_LOCATIONS = this.WEB_SERVICE_ENDPOINT + 'locations';
  WEB_SERVER_ROOMS = this.WEB_SERVICE_ENDPOINT + 'meetingHalls';

  public SERVER_ADD_TUTORIAL_MATERIAL = this.SERVER_TUTORIALS_URL + '/addTutorialMaterial';
  public SERVER_FIND_TUTORIAL_MATERIAL_BY_ID = this.SERVER_TUTORIALS_URL + '/materialTutorial?id=';
  public SERVER_GET_MATERIALS_FOR_TUTORIAL = this.SERVER_TUTORIALS_URL + '/materialsForTutorial?id=';
  public SERVER_SEARCH_TUTORIAL_BY_KEYWORD = this.SERVER_TUTORIALS_URL + '?keyword=';
  public SERVER_SEARCH_TUTORIAL_BY_ID = this.SERVER_TUTORIALS_URL + '/';
  public SERVER_DELETE_TUTORIAL = this.SERVER_TUTORIALS_URL + '/deleteTutorial';
  public SERVER_DELETE_MATERIAL = this.WEB_SERVICE_ENDPOINT + 'material/delete?id=';
  public SERVER_UPADATE_TUTORIAL = this.SERVER_TUTORIALS_URL + '/update';
  public SERVER_GET_DRAFTS_TUTORIAL = this.SERVER_TUTORIALS_URL + '/draft?idUser=';
  public SERVER_PAST_EVENT_FILTER_BY_KEYWORD = this.SERVER_PAST_EVENT + '?keyword=';
  public SERVER_UPCOMING_EVENT_FILTER_BY_KEYWORD = this.SERVER_UPCOMING_EVENT + '?keyword=';

}
