import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {RootConst} from "./util/RootConst";
import {Observable} from "rxjs/Observable";
import {UserDTO} from "./user";

@Injectable()
export class UserService {
  private rootConst:RootConst= new RootConst();
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) { }

  login(user: UserDTO): Observable<UserDTO> {
    let body=JSON.stringify({username: user.username,password:user.password});
    return this.http.post<UserDTO>(this.rootConst.WEB_SERVICE_ENDPOINT+"/auth",body,this.httpOptions);
  }

}
