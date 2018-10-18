import {Injectable} from '@angular/core';
import {Department} from '../domain/Department';
import {Observable} from 'rxjs/Observable';
import {RootConst} from '../util/RootConst';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class DepartmentService {
  private rootConst = new RootConst();

  constructor(private http: HttpClient) {
  }

  getAllDepartments(): Observable<Department[]> {
    return this.http.get<Department[]>(this.rootConst.SERVER_DEPARTMENTS);
  }
}
