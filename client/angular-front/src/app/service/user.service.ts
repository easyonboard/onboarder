import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {UserDTO, UserInformationDTO} from '../domain/user';
import {RootConst} from '../util/RootConst';
import {Course} from '../domain/course';

import {of} from 'rxjs/observable/of';
import {tap} from 'rxjs/operators';

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
    let body = JSON.stringify({username: user.username, password: user.password});
    return this.http.post<UserDTO>(this.rootConst.SERVER_AUTHENTIFICATION, body, this.httpOptions);
  }

  addUser(user: UserDTO) {
    let body = JSON.stringify(user);
    return this.http.post<UserDTO>(this.rootConst.SERVER_ADD_USER, body, this.httpOptions);
  }

  addUserInfo(userInfo: UserInformationDTO) {
    let body = JSON.stringify({team: userInfo.team, building: userInfo.building, store: userInfo.store, buddyUser: userInfo.buddyUser});
    return this.http.post<UserDTO>(this.rootConst.SERVER_ADD_USER_INFO, body, this.httpOptions);
  }

  updateUser(userDTO: UserDTO): any {
    let body = JSON.stringify({
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
    console.log('in search');

    if (term && term.length > 0) {
      if ((!term.trim())) {
        console.log('------>' + term);
        return;
      }

      let users = this.http.get<UserDTO[]>(this.rootConst.SERVER_USER_NAME + term);
      console.log('dupa http');
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

  saveCheckList(user: UserDTO, checkList: Map<string, boolean>): Observable<Map<string, boolean>> {
    const body = JSON.stringify({user: user, checkList: Array.from(checkList.entries())});
    return this.http.post<Map<string, boolean>>(this.rootConst.WEB_SERVER_SAVE_CHECKLIST, body, this.httpOptions);
  }
}
