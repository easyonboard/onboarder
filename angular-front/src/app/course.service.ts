import { Injectable } from '@angular/core';
import {Observable} from "rxjs/Observable";
import {Course} from "./course";
import {of} from "rxjs/observable/of";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class CourseService {

   constructor(private http: HttpClient,) { }
  // searchCourses(term: string): Observable<Course[]> {
  //   if (!term.trim()) {
  //     return of([]);
  //   }
  //   return this.http.get<Course[]>(`api/heroes/?name=${term}`
  //   );
  // }

}
