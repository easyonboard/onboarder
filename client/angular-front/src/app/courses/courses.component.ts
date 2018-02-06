import {Component, OnInit} from '@angular/core';
import {Course} from "../domain/course";
import {CourseService} from "../service/course.service";
import {Subject} from "rxjs/Subject";
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {Observable} from "rxjs/Observable";
import {RootConst} from "../util/RootConst";
import {Location} from '@angular/common';
import {UtilityService} from "../service/utility.service";
import {UserService} from "../service/user.service";

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

  constructor(private courseService: CourseService, private location: Location, private userService: UserService, private utilityService: UtilityService) {}

  ngOnInit(): void {
    this.message = "";
    this.successMessage = "";
    this.getCourses();
    this.coursesSearched$ = this.searchTerms.pipe(
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      switchMap((term: string) => this.courseService.searchCourses(term)));
  }

  getCourses(): void {
    this.courseService.findCourses().subscribe(courses => this.courses = courses);
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  openModal(id: string) {
    this.utilityService.openModal(id);
  }

  closeModal(id: string) {
    this.utilityService.closeModal(id);
  }
}