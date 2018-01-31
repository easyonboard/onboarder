import {Injectable} from '@angular/core';
import {Course} from "../domain/course";
import {Subject} from "../domain/subject";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {RootConst} from "../util/RootConst";

@Injectable()
export class SubjectService {

  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  addSubject(subject: Subject, course:Course): any {
    debugger
    let body = JSON.stringify({subject:subject,course:course});
    return this.http.post<Course>(this.rootConst.SERVER_ADD_SUBJECT, body, this.httpOptions);
  }

}
