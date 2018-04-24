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

  updateUserInformation(userInfo: UserInformationDTO): Observable<UserInformationDTO> {
    let body = JSON.stringify({
      idUserInformation: userInfo.idUserInformation,
      team: userInfo.team, building: userInfo.building, floor: userInfo.floor, buddyUser: userInfo.buddyUser,
      userAccount: userInfo.userAccount, mailSent: userInfo.mailSent, startDate: userInfo.startDate
    });

    return this.http.put<UserInformationDTO>(this.rootConst.SERVER_UPDATE_USER_INFO, body, this.httpOptions);
  }

  getNewUsers(): Observable<UserInformationDTO[]> {
    return this.http.get<UserInformationDTO[]>(this.rootConst.WEB_SERVER_NEWUSERS);
  }

}
