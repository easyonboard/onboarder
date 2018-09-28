import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RootConst} from '../util/RootConst';
import {LocationDTO} from '../domain/location';
import {UserDTO} from '../domain/user';

@Injectable()
export class UserInformationService {

  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  updateUserInformation(userInfo: UserDTO): Observable<UserDTO> {
    const body = JSON.stringify(userInfo);
    return this.http.post<UserDTO>(this.rootConst.SERVER_UPDATE_USER_INFO, body, this.httpOptions);

  }

  getNewUsers(): Observable<UserDTO[]> {
    return this.http.get<UserDTO[]>(this.rootConst.SERVER_NEWUSERS);
  }

  getUserInformation(username: string): Observable<UserDTO> {

    return this.http.post<UserDTO>(this.rootConst.SERVER_USERINFORMATION, username, this.httpOptions);

  }

  getAllLocations(): Observable<LocationDTO[]> {
    return this.http.get<LocationDTO[]>(this.rootConst.SERVER_LOCATIONS);
  }
}
