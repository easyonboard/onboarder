import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Course} from "./course";
import {Observable} from "rxjs/Observable";
import {of} from "rxjs/observable/of";

@Injectable()
export class CourseService {

  private webServiceEndpoint= 'http://localhost:8090/';
   constructor(private http: HttpClient) { }


   findCourses(): Observable<Course[]>{

     return this.http.get<Course[]>(`${this.webServiceEndpoint}/courses`);
   }
   searchCourses(value: string): Observable<Course[]>{

     if(!value.trim())
       return this.http.get<Course[]>(`${this.webServiceEndpoint}/courses`);
     return this.http.get<Course[]>(`${this.webServiceEndpoint}/course?overview=${value}`);
   }
}
