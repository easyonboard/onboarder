import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RootConst} from '../util/RootConst';
import {UserInformationDTO} from '../domain/user';

@Injectable()
export class UserInformationService {

  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  addUserInformation(userInfo: UserInformationDTO): Observable<UserInformationDTO> {
    let body = JSON.stringify({
      team: userInfo.team, building: userInfo.building, store: userInfo.store, buddyUser: userInfo.buddyUser,
      userAccount: userInfo.userAccount, mailSent: userInfo.mailSent, startDate: userInfo.startDate
    });

    return this.http.post<UserInformationDTO>(this.rootConst.SERVER_ADD_USER_INFO, body, this.httpOptions);
  }

  updateUserInformation(userInfo: UserInformationDTO): Observable<UserInformationDTO> {
    console.log('=================>' + userInfo.store);
    let body = JSON.stringify({
      idUserInformation: userInfo.idUserInformation,
      team: userInfo.team, building: userInfo.building, store: userInfo.store, buddyUser: userInfo.buddyUser,
      userAccount: userInfo.userAccount, mailSent: userInfo.mailSent, startDate: userInfo.startDate
    });

    return this.http.put<UserInformationDTO>(this.rootConst.SERVER_UPDATE_USER_INFO, body, this.httpOptions);
  }

  getNewUsers(): Observable<UserInformationDTO[]> {
    return this.http.get<UserInformationDTO[]>(this.rootConst.WEB_SERVER_NEWUSERS);
  }

}
