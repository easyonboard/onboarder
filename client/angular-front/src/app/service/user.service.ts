import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs/Observable';
import {UserDTO} from '../domain/user';
import {RootConst} from '../util/RootConst';
import {Course} from '../domain/course';

import {of} from 'rxjs/observable/of';
import {tap} from 'rxjs/operators';
import {UserInformationDTO} from '../domain/userinformation';

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
    console.log('from service POST: ' + JSON.stringify({username: user.username, password: user.password,
      email: user.email, name: user.name}));

    return this.http.post<UserDTO>(this.rootConst.SERVER_ADD_USER,
        JSON.stringify({username: user.username, password: user.password,
        email: user.email, name: user.name}), this.httpOptions);
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

  getProgress(course: Course, user: UserDTO): Observable<number> {
    const body = JSON.stringify({user: user, course: course});
    return this.http.post<number>(this.rootConst.WEB_SERVER_PROGRESS, body, this.httpOptions);

  }

  getUserByUsername(term: string): Observable<UserDTO[]> {
    if (!term.trim()) {
      // if not search term, return empty hero array.
      return of([]);
    }
    return this.http.get<UserDTO[]>(this.rootConst.SERVER_USER_USERNAME + term)
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
