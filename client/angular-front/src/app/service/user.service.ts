import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {LeaveCheckList, User} from '../domain/user';
import {ServerURLs} from '../util/ServerURLs';
import {TSMap} from 'typescript-map';
import {RoleType} from '../domain/role';
import {InterceptorExpiredTokenGuard} from '../guard/interceptor-expired-token.guard';

@Injectable()
export class UserService {
  public message: string;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  private allUsers: string = ServerURLs.ALL_USERS;

  constructor(private http: HttpClient, private interceptorExpiredToken: InterceptorExpiredTokenGuard) {
    this.message = '';
  }

  addUser(user: User) {
    const body = JSON.stringify({user: user});

    return this.http.post<User>(ServerURLs.ADD_USER, body, this.httpOptions);
  }


  getAllUsers(): Observable<User[]> {
    return this.http.get<User[]>(`${this.allUsers}`);
  }
  
  getAllMsgMails(): Observable<String[]> {
    return this.http.get<String[]>(ServerURLs.ALL_MSG_MAILS);
  }

  searchUsers(term: string): Observable<User[]> {

    if (term && term.length > 0) {
      if ((!term.trim())) {
        return;
      }

      const users = this.http.get<User[]>(ServerURLs.USER_NAME + term);
      return users;
    }
    // in case term is undefined, we don't want to make a request to the server with a null param, so we return an empty observable
    return Observable.empty<User[]>();
  }


  getCheckListForUser(user: User): Observable<Map<string, boolean>> {
    const body = JSON.stringify(user);
    return this.http.post<Map<string, boolean>>(ServerURLs.CHECKLIST, body, this.httpOptions);

  }

  saveCheckList(user: string, checkList: TSMap<string, boolean>): Observable<Map<string, boolean>> {
    const body = JSON.stringify({user: user, check: checkList.toJSON()});
    return this.http.post<Map<string, boolean>>(ServerURLs.SAVE_CHECKLIST, body, this.httpOptions);
  }

  getUsersInDepartment(username: string): Observable<User[]> {
    return this.http.get<User[]>(ServerURLs.USER_DEPARTMENT_FOR_USERNAME + username);
  }


  removeUser(username: String): Observable<Boolean> {

    return this.http.post<Boolean>(ServerURLs.REMOVE_USER, username, this.httpOptions);

  }

  isMailSent(userAccount: User): Observable<Boolean> {

    return this.http.post<Boolean>(ServerURLs.STATUS_MAIL, userAccount.username, this.httpOptions);

  }

  getUserLeaveCheckList(user: string): Observable<LeaveCheckList> {

    return this.http.post<LeaveCheckList>(ServerURLs.LEAVE_CHECKLIST, user, this.httpOptions);
  }

  saveLeaveCheckList(leaveCheckList: LeaveCheckList): Observable<LeaveCheckList> {
    const body = JSON.stringify(leaveCheckList);
    const result = this.http.post<LeaveCheckList>(ServerURLs.SAVE_LEAVE_CHECKLIST, body, this.httpOptions);
    console.log(result);
    return result;
  }

  checkUnicity(username: String, msgMail: String): Observable<boolean> {
    const body = JSON.stringify({username: username, msgMail: msgMail});
    return this.http.post<boolean>(ServerURLs.CHECK_USER_UNICITY, body, this.httpOptions);
  }

  getUserByUsername(username: string): Observable<User> {
    // if (username) {
    return this.http.get<User>(`${ServerURLs.USER_BY_USERNAME}${username}`);
    // } else {
    // throw error;
    // }
  }

  getNewUsers(): Observable<User[]> {
    return this.http.get<User[]>(ServerURLs.NEW_USERS);
  }

  updateUser(user: any) {
    const body = JSON.stringify({user: user});
    console.log(body);
    return this.http.post<User>(ServerURLs.UPDATE_USER, user, this.httpOptions);
  }
}
