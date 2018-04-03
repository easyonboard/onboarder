import {Component, OnInit} from '@angular/core';
import {Course} from '../domain/course';
import {CourseService} from '../service/course.service';
import {Subject} from 'rxjs/Subject';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {Observable} from 'rxjs/Observable';
import {RootConst} from '../util/RootConst';
import {ScrollEvent} from 'ngx-scroll-event';
import {Const} from '../util/Const';
import {UtilityService} from '../service/utility.service';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css'],
})
export class CoursesComponent implements OnInit {
  public rootConst: RootConst = new RootConst();
  courses: Course[];
  message: string;
  coursesSearched$: Observable<Course[]>;
  public successMessage: string;
  private searchTerms = new Subject<string>();
  public coursesListEmpty: boolean;
  private pageNumber: number;
  private searchPageNumber: number;
  public messageDeleteCourse: String;


  constructor(private courseService: CourseService, public utilityService: UtilityService) {

    this.pageNumber = 0;
    this.searchPageNumber = 0;
    this.coursesListEmpty = true;
    this.courses = [];
  }

  setCourseList(courses: Course[]) {
    this.courses = courses;
  }

  getCoursesFromPage(): void {
    this.courseService.getCoursesByPageNumberAndNumberOfObjectsPerPage(this.pageNumber, Const.NUMBER_OF_OBJECTS_PER_PAGE).subscribe(courses => {
        this.courses = this.courses.concat(courses), this.coursesListEmpty = false;
      },
      err => {
        if (err.statusText == 'Unknown Error')
          setTimeout(() => this.getCoursesFromPage(), 10000);
      });
  }

  private filterCoursesByKeyword(keyword: string) {
    this.courseService.filterCoursesKeywordPageNumberAndNumberOfObjectsPerPage(keyword).subscribe(courses => {
        this.courses = this.courses.concat(courses), this.coursesListEmpty = false;
      },
      err => {
        if (err.statusText == 'Unknown Error')
          setTimeout(() => this.filterCoursesByKeyword(keyword), 10000);
      });
  }

  search(term: string): void {
    this.searchTerms.next(term);

  }

  handleScroll(event: ScrollEvent): any {
    if (event.isReachingBottom) {

      this.pageNumber++;
      console.log(`the user is reaching the bottom`);
      // this.getCoursesFromPage();
      var keyword = new URL(window.location.href).searchParams.get('keyword');
      if (keyword == null) {
        this.getCoursesFromPage();
      }
    }
  }

  deleteCourses(idCourse: number) {
    this.courseService.deleteCourse(idCourse).subscribe(res => {
        this.pageNumber = 0;
        this.courses = [];
        this.getCoursesFromPage();
        this.messageDeleteCourse = 'Course Deleted!';
        this.utilityService.openModal('delete');

      },
      err => {
        this.messageDeleteCourse = err.error.message;


        this.utilityService.openModal('delete');
      });
  }

  searchByKeyword(keyword: string) {
    location.href = this.rootConst.FRONT_COURSES_PAGE_SEARCH_BY_KEYWORD + keyword;
  }

  ngOnInit(): void {
    this.pageNumber = 0;
    this.message = '';
    this.successMessage = '';
    debugger;
    var keyword = new URL(window.location.href).searchParams.get('keyword');
    if (keyword == null) {
      this.getCoursesFromPage();
    } else {
      this.filterCoursesByKeyword(keyword);
    }
    this.coursesSearched$ = this.searchTerms.pipe(
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      switchMap((term: string) => this.courseService.searchCourses(term)));
  }


}
