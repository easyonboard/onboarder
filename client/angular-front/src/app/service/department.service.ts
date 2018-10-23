import {Injectable} from '@angular/core';
import {Department} from '../domain/Department';
import {Observable} from 'rxjs/Observable';
import {ServerURLs} from '../util/ServerURLs';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class DepartmentService {
  constructor(private http: HttpClient) {
  }

  getAllDepartments(): Observable<Department[]> {
    return this.http.get<Department[]>(ServerURLs.ALL_DEPARTMENTS);
  }
}
