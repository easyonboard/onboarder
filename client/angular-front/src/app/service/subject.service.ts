import {Injectable} from '@angular/core';
import {Course} from '../domain/course';
import {Subject} from '../domain/subject';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {RootConst} from '../util/RootConst';
import {UserDTO} from '../domain/user';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class SubjectService {

  private rootConst: RootConst = new RootConst();
  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  addSubject(subject: Subject, course: Course): any {
    debugger;
    let body = JSON.stringify({subject: subject, course: course});
    return this.http.post<Subject>(this.rootConst.SERVER_ADD_SUBJECT, body, this.httpOptions);
  }

  markAsFinish(subject: Subject, user: UserDTO): any {

    const body = JSON.stringify({subject: subject, user: user});
    return this.http.post(this.rootConst.WEB_SERVICE_MARK_AS_FINISHED, body, this.httpOptions);


  }

  getStatusSubjects(course: Course, user: UserDTO): Observable<Boolean[]> {

    const body=JSON.stringify({user: user, course: course});
    return this.http.post<Boolean[]>(this.rootConst.WEB_SERVICE_SUBJECT_STATUS, body, this.httpOptions);

  }
}
