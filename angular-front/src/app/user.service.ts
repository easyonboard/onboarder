import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {RootConst} from "./util/RootConst";
import {Observable} from "rxjs/Observable";
import {UserDTO} from "./domain/user";
import {catchError} from "rxjs/operators";
import {of} from "rxjs/observable/of";

@Injectable()
export class UserService {
  private rootConst:RootConst= new RootConst();
  public message:string;
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  httpOptionsAuthorize = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json', "Authorization": "Basic"})
  };

  constructor(private http: HttpClient) {this.message=""; }

  login(user: UserDTO): Observable<UserDTO> {
    let body = JSON.stringify({username: user.username, password: user.password});
    return this.http.post<UserDTO>(this.rootConst.SERVER_AUTHENTIFICATION, body, this.httpOptionsAuthorize);

  }


  addUser(user: UserDTO) {
    let body = JSON.stringify({username: user.username, password: user.password,email:user.email,name:user.name});
    return this.http.post<UserDTO>(this.rootConst.SERVER_ADD_USER, body, this.httpOptions);

  }
  updateUser(userDTO:UserDTO):any {
    let body = JSON.stringify({username: userDTO.username, password: userDTO.password,email:userDTO.email,name:userDTO.name});
    return this.http.post<UserDTO>(this.rootConst.SERVER_UPDATE_USER, body, this.httpOptions);
  }

  getAllUserEmails():Observable<string[]> {
    return this.http.get<string[]>(this.rootConst.SERVER_USERS_EMAIL);

  }
}
