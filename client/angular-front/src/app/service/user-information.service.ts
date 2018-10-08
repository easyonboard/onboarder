import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RootConst} from '../util/RootConst';
import {Location} from '../domain/location';
import {User} from '../domain/user';
import {Department} from '../domain/Department';

@Injectable()
export class UserInformationService {

  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  updateUserInformation(userInfo: User): Observable<User> {
    const body = JSON.stringify(userInfo);
    return this.http.post<User>(this.rootConst.SERVER_UPDATE_USER_INFO, body, this.httpOptions);

  }

  getNewUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.rootConst.SERVER_NEWUSERS);
  }

  getUserInformation(username: string): Observable<User> {

    return this.http.post<User>(this.rootConst.SERVER_USERINFORMATION, username, this.httpOptions);

  }

  getAllLocations(): Observable<Location[]> {
    return this.http.get<Location[]>(this.rootConst.SERVER_LOCATIONS);
  }

  getAllDepartments(): Observable<Department[]> {
    return this.http.get<Department[]>(this.rootConst.SERVER_DEPARTMENTS);
  }
}
