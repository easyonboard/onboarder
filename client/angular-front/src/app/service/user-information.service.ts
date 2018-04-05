import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RootConst} from '../util/RootConst';
import {UserInformationDTO} from '../domain/user';

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
    console.log('=================>' + userInfo.buddyUser.name);
    console.log('=================>' + userInfo.store);
    let body = JSON.stringify({
      building: userInfo.building, store: userInfo.store,
      team: userInfo.team, buddyUser: userInfo.buddyUser, userAccount: userInfo.userAccount, mailSent: userInfo.mailSent
    });
    return this.http.put<UserInformationDTO>(this.rootConst.SERVER_UPDATE_USER_INFO, body, this.httpOptions);

  }

  getNewUsers(): Observable<UserInformationDTO[]> {
    return this.http.get<UserInformationDTO[]>(this.rootConst.WEB_SERVER_NEWUSERS);
  }

}
