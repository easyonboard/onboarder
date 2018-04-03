import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RootConst} from '../util/RootConst';
import {UserInformationDTO} from "../domain/user";

@Injectable()
export class UserInformationService {

  private userInfoURL = 'http://localhost:4200/info';  // URL to web api
  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  addUserInformation(userInfo: UserInformationDTO): Observable<UserInformationDTO> {
    let body = JSON.stringify({building: userInfo.building, store: userInfo.floor, team: userInfo.team, buddy: userInfo.buddy});
    return this.http.post<UserInformationDTO>(this.rootConst.SERVER_ADD_USER, body, this.httpOptions);
  }

  getNewUsers(): Observable<UserInformationDTO[]> {
    return this.http.get<UserInformationDTO[]>(this.rootConst.WEB_SERVER_NEWUSERS);
  }

}
