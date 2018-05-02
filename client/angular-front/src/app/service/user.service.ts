import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {UserDTO, UserInformationDTO} from '../domain/user';
import {RootConst} from '../util/RootConst';
import {Course} from '../domain/course';

import {of} from 'rxjs/observable/of';
import {tap} from 'rxjs/operators';
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

  login(user: UserDTO): Observable<UserDTO> {
    const body = JSON.stringify({username: user.username, password: user.password});
    return this.http.post<UserDTO>(this.rootConst.SERVER_AUTHENTIFICATION, body, this.httpOptions);
  }

  addUser(user: UserDTO, role: RoleType, userInfo: UserInformationDTO) {
    console.log('user stuff ' + user.email + '\n');
    console.log('role stuff ' + role + '\n');

    const body = JSON.stringify({user: user, role: role, userInfo: userInfo});
    return this.http.post<UserDTO>(this.rootConst.SERVER_ADD_USER, body, this.httpOptions);
  }

  updatePassword(username: string, password: string) {
    const body = JSON.stringify({
      username: username,
      password: password,
    });
    return this.http.post<UserDTO>(this.rootConst.SERVER_UPDATE_USER_PASSWORD, body, this.httpOptions);
  }

  updateUser(userDTO: UserDTO): any {
    const body = JSON.stringify({
      username: userDTO.username,
      password: userDTO.password,
      email: userDTO.email,
      name: userDTO.name
    });
    return this.http.post<UserDTO>(this.rootConst.SERVER_UPDATE_USER, body, this.httpOptions);
  }

  getAllUsers(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(`${this.allUsers}`);
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

  getProgress(course: Course, user: UserDTO): Observable<number> {
    const body = JSON.stringify({user: user, course: course});
    return this.http.post<number>(this.rootConst.WEB_SERVER_PROGRESS, body, this.httpOptions);

  }

  getUserByUsername(term: string): Observable<UserDTO[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<UserDTO[]>(this.rootConst.SERVER_USER_NAME + term)
      .pipe(tap(_ => console.log(`found heroes matching '${term}'`)));
  }

  getCheckListForUser(user: UserDTO): Observable<Map<string, boolean>> {
    const body = JSON.stringify(user);
    return this.http.post<Map<string, boolean>>(this.rootConst.WEB_SERVER_CHECKLIST, body, this.httpOptions);

  }

  saveCheckList(user: string, checkList: TSMap<string, boolean>): Observable<Map<string, boolean>> {
    const body = JSON.stringify({user: user, check: checkList.toJSON()});
    return this.http.post<Map<string, boolean>>(this.rootConst.WEB_SERVER_SAVE_CHECKLIST, body, this.httpOptions);
  }

  getUsersInDepartment(username: string): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(this.rootConst.SERVER_LOGGED_USER_DEPARTMENT + username);
  }

  getDepartmentForUsername(username): Observable<any> {
    return this.http.get<any>(this.rootConst.SERVER_LOGGED_USER_DEPARTMENT + username);
  }

  removeUser(username: String) {

    return this.http.post(this.rootConst.REMOVE_USER, username, this.httpOptions);

  }

  isMailSent(userAccount: UserDTO): Observable<Boolean> {

    return this.http.post<Boolean>(this.rootConst.WEB_SERVER_STATUS_MAIL, userAccount.username, this.httpOptions);

  }
}
