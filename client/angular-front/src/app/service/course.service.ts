import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Course} from '../domain/course';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import {Subject} from '../domain/subject';
import {UserDTO} from '../domain/user';
import {RootConst} from '../util/RootConst';

@Injectable()
export class CourseService implements OnInit {

  private rootConst: RootConst = new RootConst();
  private coursesByPageNumberUrl = this.rootConst.SERVER_COURSES_BY_PAGE_NUMBER_URL;
  private numberOfObjectsPerPageUrl = this.rootConst.SERVER_COURSES_NUMBER_OF_OBJECT_PER_PAGE;

  private searchByCourseOverviewUrl = this.rootConst.SERVER_COURSE_OVERVIEW;


  private detailedCourse = this.rootConst.SERVER_DETAILED_COURSE;
  private testIfUserIsEnroll = this.rootConst.SERVER_TEST_IF_USER_IS_ENROLLED;
  private enrolleUserOnCourse = this.rootConst.SERVER_ENROLLE_USER_ON_COURSE;
  private unenrolleUserOnCourse = this.rootConst.SERVER_UNENROLLE_USER_ON_COURSE;
  private detailedSubject = this.rootConst.SERVER_SUBJECT_URL;
  private detailedSubject2 = this.rootConst.SERVER_SUBJECT_URL2;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  getCoursesByPageNumberAndNumberOfObjectsPerPage(pageNumber: number, numberOfObjectsPerPageUrl: number) {
    return this.http.get<Course[]>(`${this.coursesByPageNumberUrl}${pageNumber}${this.numberOfObjectsPerPageUrl}${numberOfObjectsPerPageUrl}`);
  }

  searchCourses(value: string): Observable<Course[]> {
    if (!value.trim())
      return;
    return this.http.get<Course[]>(`${this.searchByCourseOverviewUrl}${value}`);
  }

  getCourse(id: number): Observable<Course> {
    return this.http.get<Course>(`${this.detailedCourse}${id}`);
  }

  isUserEnrollOnThisCourse(idCourse: number, username: String): Observable<Boolean> {
    return this.http.post<Boolean>(`${this.testIfUserIsEnroll}${idCourse}`, username);
  }

  enrollUserToCourse(idCourse: number, username: String): Observable<any> {
    return this.http.post(`${this.enrolleUserOnCourse}${idCourse}`, username);
  }


  unenrollUserFromCourse(idCourse: number, username: string): Observable<any> {
    return this.http.post(`${this.unenrolleUserOnCourse}${idCourse}`, username);

  }

  editCourse(course: Course, contactPersonsIds: number[], ownersIds: number[]): Observable<any> {
    let body = JSON.stringify({course: course, contactPersonsId: contactPersonsIds, ownersIds: ownersIds});
    return this.http.post<Course>(this.rootConst.SERVER_COURSES_URL + '/updateCourse', body, this.httpOptions);
  }

  getSubject(id: number, idSubject: number): Observable<Subject> {
    return this.http.get<Subject>(`${this.detailedSubject}${id}${this.detailedSubject2}${idSubject}`);
  }

  deleteContactPerson(contactPerson: UserDTO, course: Course) {
    let body = JSON.stringify({user: contactPerson, course: course});
    return this.http.post<Course>(this.rootConst.SERVER_DELETE_CONTACT_PERSON, body, this.httpOptions);
  }

  deleteOwnerPerson(contactPerson: UserDTO, course: Course) {
    let body = JSON.stringify({user: contactPerson, course: course});
    return this.http.post<Course>(this.rootConst.SERVER_DELETE_OWNER_PERSON, body, this.httpOptions);

  }

  deleteSubjectFromCourse(subject: number, course: Course) {

    let body = JSON.stringify({subject: subject, course: course});
    return this.http.post<Course>(this.rootConst.SERVER_DELETE_SUBJECT, body, this.httpOptions);
  }

  addContactPerson(email: string, course: Course): Observable<Course> {

    let body = JSON.stringify({email: email, course: course});
    return this.http.post<Course>(this.rootConst.SERVER_ADD_CONTACT_PERSON, body, this.httpOptions);

  }

  addOwnerPerson(email: string, course: Course): Observable<Course> {

    let body = JSON.stringify({email: email, course: course});
    return this.http.post<Course>(this.rootConst.SERVER_ADD_OWNER_PERSON, body, this.httpOptions);

  }

  addCourse(course: Course, ownersIds: number[], contactPersonsIds: number[]): any {
    let body = JSON.stringify({course: course, ownersIds: ownersIds, contactPersonsId: contactPersonsIds});
    return this.http.post<Course>(this.rootConst.SERVER_ADD_COURSE, body, this.httpOptions);
  }

  deleteCourse(idCourse): Observable<any> {
    const body = JSON.stringify({idCourse: idCourse});
    return this.http.post<Course>(this.rootConst.SERVER_DELETE_COURSE, body, this.httpOptions);
  }
  ngOnInit(): void {
  }


  getEnrolledCoursesForUser(username: string):Observable<Course[]> {
    return this.http.get<Course[]>(`${this.rootConst.SERVER_USER_COURSES}${username}`);
  }

  filterCoursesKeywordPageNumberAndNumberOfObjectsPerPage(keyword: string) {
    return this.http.get<Course[]>(`${this.rootConst.SERVER_FILTER_COURSES_BY_KEYWORD}${keyword}`);

  }
}
