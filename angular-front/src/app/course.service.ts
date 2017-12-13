import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Course} from "./course";
import {Observable} from "rxjs/Observable";
import {of} from "rxjs/observable/of";
import 'rxjs/add/operator/map';
import {RootConst} from "./util/RootConst";

@Injectable()
export class CourseService implements OnInit {

  private rootConst:RootConst= new RootConst();
  private coursesUrl = this.rootConst.SERVER_COURSES_URL;
  private courseOverview = this.rootConst.SERVER_COURSE_OVERVIEW;
  private detailedCourse = this.rootConst.SERVER_DETAILED_COURSE;
  private testIfUserIsEnroll = this.rootConst.SERVER_TEST_IF_USER_IS_ENROLLED;
  private enrolleUserOnCourse = this.rootConst.SERVER_ENROLLE_USER_ON_COURSE;
  private unenrolleUserOnCourse = this.rootConst.SERVER_UNENROLLE_USER_ON_COURSE;



  constructor(private http: HttpClient) {
  }

  findCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(`${this.coursesUrl}`);
  }

  searchCourses(value: string): Observable<Course[]> {
    if (!value.trim())
      return this.http.get<Course[]>(`${this.coursesUrl}`);
    return this.http.get<Course[]>(`${this.courseOverview}${value}`);
  }

  getCourse(id: number) {
    return this.http.get<Course>(`${this.detailedCourse}${id}`);
  }

  isUserEnrollOnThisCourse(idCourse: number, username:String):Observable<Boolean>{
    return this.http.post<Boolean>(`${this.testIfUserIsEnroll}${idCourse}`,username)
  }

  enrollUserToCourse(idCourse:number, username:String):Observable<any>{
    return this.http.post(`${this.enrolleUserOnCourse}${idCourse}`,username);
  }

  ngOnInit(): void {
  }

  unenrollUserFromCourse(idCourse: number, username: string):Observable<any> {
    return this.http.post(`${this.unenrolleUserOnCourse}${idCourse}`,username);

  }
}
