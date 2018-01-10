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
import {UserDTO} from "../domain/user";
import {UserService} from "../user.service";
import {Ng2OrderModule} from "ng2-order-pipe/dist/index";
import {UtilityService} from "../utility.service";

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.css'],
})
export class CoursesComponent implements OnInit {
  public rootConst:RootConst= new RootConst();
  courses: Course[];
  message:string;
  coursesSearched$: Observable<Course[]>;
  public successMessage:string;
  constructor(private courseService: CourseService, private location:Location, private userService: UserService, private utilityService:UtilityService) { }
   myList:Course[]=[];
  ngOnInit():void {
    this.message="";
    this.successMessage="";
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


  search(term: string): void {
    this.searchTerms.next(term);
  }

    openModal(id:string){
  this.utilityService.openModal(id);
}
  closeModal(id:string){
    this.utilityService.closeModal(id);
  }
}
