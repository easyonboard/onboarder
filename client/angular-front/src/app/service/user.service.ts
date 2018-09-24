import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {LeaveCheckList, UserDTO, UserInformationDTO} from '../domain/user';
import {RootConst} from '../util/RootConst';
import {TSMap} from 'typescript-map';
import {RoleType} from '../domain/role';

@Injectable()
export class UserService {
  private rootConst: RootConst = new RootConst();
  public message: string;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  private allUsers: string = this.rootConst.SERVER_ALL_USERS;

  constructor(private http: HttpClient) {
    this.message = '';
  }

  addUser(user: UserDTO, role: RoleType, userInfo: UserInformationDTO) {
    const body = JSON.stringify({user: user, role: role, userInfo: userInfo});
    const result = this.http.post<UserDTO>(this.rootConst.SERVER_ADD_USER, body, this.httpOptions);
    return result;
  }

  updatePassword(username: string, password: string) {
    const body = JSON.stringify({
      username: username,
      password: password,
    });
    const result = this.http.post<UserDTO>(this.rootConst.SERVER_UPDATE_USER_PASSWORD, body, this.httpOptions);
    console.log(result);
    return result;
  }

  getAllUsers(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.allUsers}`);
  }

  /** !TODO */
  getAllMsgMails(): Observable<String[]> {
    return this.http.get<String[]>(this.rootConst.SERVER_ALL_MSG_MAILS);
  }

  searchUsers(term: string): Observable<UserDTO[]> {

    if (term && term.length > 0) {
      if ((!term.trim())) {
        return;
      }

      const users = this.http.get<UserDTO[]>(this.rootConst.SERVER_USER_NAME + term);
      return users;
    }
    // in case term is undefined, we don't want to make a request to the server with a null param, so we return an empty observable
    return Observable.empty<UserDTO[]>();
  }


  getCheckListForUser(user: UserDTO): Observable<Map<string, boolean>> {
    const body = JSON.stringify(user);
    return this.http.post<Map<string, boolean>>(this.rootConst.SERVER_CHECKLIST, body, this.httpOptions);

  }

  saveCheckList(user: string, checkList: TSMap<string, boolean>): Observable<Map<string, boolean>> {
    const body = JSON.stringify({user: user, check: checkList.toJSON()});
    return this.http.post<Map<string, boolean>>(this.rootConst.SERVER_SAVE_CHECKLIST, body, this.httpOptions);
  }

  getUsersInDepartment(username: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(this.rootConst.SERVER_LOGGED_USER_DEPARTMENT + username);
  }


  removeUser(username: String): Observable<Boolean> {

    return this.http.post<Boolean>(this.rootConst.SERVER_REMOVE_USER, username, this.httpOptions);

  }

  isMailSent(userAccount: UserDTO): Observable<Boolean> {

    return this.http.post<Boolean>(this.rootConst.SERVER_STATUS_MAIL, userAccount.username, this.httpOptions);

  }

  getUserLeaveCheckList(user: string): Observable<LeaveCheckList> {

    return this.http.post<LeaveCheckList>(this.rootConst.SERVER_LEAVE_CHECKLIST, user, this.httpOptions);
  }

  saveLeaveCheckList(leaveCheckList: LeaveCheckList): Observable<LeaveCheckList> {
    const body = JSON.stringify(leaveCheckList);
    const result = this.http.post<LeaveCheckList>(this.rootConst.SERVER_SAVE_LEAVE_CHECKLIST, body, this.httpOptions);
    console.log(result);
    return result;
  }

  checkUnicity(username: String, msgMail: String): Observable<boolean> {
    const body = JSON.stringify({username: username, msgMail: msgMail});
    return this.http.post<boolean>(this.rootConst.SERVER_CHECK_USER_UNICITY, body, this.httpOptions);
  }

  getUserByUsername(username: string): Observable<UserDTO> {
    // if (username) {
    return this.http.get<UserDTO>(`${this.rootConst.SERVER_USER_BY_USERNAME}${username}`);
    // } else {
    // throw error;
    // }
  }
}
