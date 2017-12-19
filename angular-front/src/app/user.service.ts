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
    headers: new HttpHeaders({ 'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {this.message=""; }

  login(user: UserDTO): Observable<UserDTO> {
    let body = JSON.stringify({username: user.username, password: user.password});
    return this.http.post<UserDTO>(this.rootConst.WEB_SERVICE_ENDPOINT + "/auth", body, this.httpOptions);

  }
  private handleError<T>( result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
    if(error.status==403) {
      this.message = "Username or password wrong. Please try again";
    }
      // TODO: better job of transforming error for user consumption


      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  addUser(user: UserDTO) {
    let body = JSON.stringify({username: user.username, password: user.password,email:user.email,name:user.name});
    return this.http.post<UserDTO>(this.rootConst.WEB_SERVICE_ENDPOINT + "/addUser", body, this.httpOptions);

  }
}
