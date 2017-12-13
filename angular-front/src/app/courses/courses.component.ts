import { Component, OnInit } from '@angular/core';
import {Course} from "../course";
import {CourseService} from "../course.service";
import {Subject} from "rxjs/Subject";
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';
import {Observable} from "rxjs/Observable";
import {debug} from "util";
import {RootConst} from "../util/RootConst";

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css'],
})
export class CoursesComponent implements OnInit {
  public rootConst:RootConst= new RootConst();
  courses: Course[];
  coursesSearched$: Observable<Course[]>;
  constructor(private courseService: CourseService) { }


  ngOnInit():void {
    this.getCourses();
    this.coursesSearched$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.courseService.searchCourses(term)));

  }
  getCourses(): void {

    this.courseService.findCourses().subscribe(courses=> this.courses=courses);
  }
  private searchTerms = new Subject<string>();

// Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }
}
