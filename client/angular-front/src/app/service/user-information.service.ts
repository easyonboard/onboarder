import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RootConst} from '../util/RootConst';
import {UserInformationDTO} from '../domain/user';
import {LocationDTO} from '../domain/location';

@Injectable()
export class UserInformationService {

  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  updateUserInformation(userInfo: UserInformationDTO): Observable<UserInformationDTO> {
    const body = JSON.stringify(userInfo);
    return this.http.post<UserInformationDTO>(this.rootConst.SERVER_UPDATE_USER_INFO, body, this.httpOptions);

  }

  getNewUsers(): Observable<UserInformationDTO[]> {
    return this.http.get<UserInformationDTO[]>(this.rootConst.SERVER_NEWUSERS);
  }

  getUserInformation(username: string): Observable<UserInformationDTO> {

    return this.http.post<UserInformationDTO>(this.rootConst.SERVER_USERINFORMATION, username, this.httpOptions);

  }

  getAllLocations(): Observable<LocationDTO[]> {
    return this.http.get<LocationDTO[]>(this.rootConst.SERVER_LOCATIONS);
  }
}
