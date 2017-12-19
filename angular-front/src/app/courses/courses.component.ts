import { Component, OnInit } from '@angular/core';
import {Course} from "../domain/course";
import {CourseService} from "../course.service";
import {Subject} from "rxjs/Subject";
import {
  debounceTime, distinctUntilChanged, switchMap
} from 'rxjs/operators';
import {Observable} from "rxjs/Observable";
import {RootConst} from "../util/RootConst";
import {Location} from '@angular/common';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css'],
})
export class CoursesComponent implements OnInit {
  public rootConst:RootConst= new RootConst();
  courses: Course[];
  coursesSearched$: Observable<Course[]>;
  constructor(private courseService: CourseService, private location:Location) { }


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

  goToPage(idC: number):void{
    location.replace(this.rootConst.FRONT_DETAILED_COURSE+"/"+idC);

}
  getCourses(): void {
    this.courseService.findCourses().subscribe(courses=> this.courses=courses);
  }
  private searchTerms = new Subject<string>();


  search(term: string): void {
    this.searchTerms.next(term);
  }
}
