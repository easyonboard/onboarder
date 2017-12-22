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

    openModal(id:string){
  this.utilityService.openModal(id);
}
  closeModal(id:string){
    this.utilityService.closeModal(id);
  }

  updateUser(name: string, email: string, password: string, passwordII: string): void {
    name = name.trim();
    email = email.trim();
    password=password.trim();
    passwordII=passwordII.trim();


    if(password!="" && (password!=passwordII || password.length<6)){
    this.message="Password not matching or does not have 6 characters"
      return;
    }


    if(email!="" && !(email.endsWith("@yahoo.com") || email.endsWith("@gmail.com"))){
      this.message="Not a valid email address!";
      return;

    }
    else{
      var username = localStorage.getItem("userLogged");
      if(name=="")
        name=null;
      if(email=="")
        email=null;
      if(password=="")
        password=null;
      this.userService.updateUser({name,username, email,password} as UserDTO).subscribe(
        res => {
          this.utilityService.closeModal('myModal');
          this.successMessage="Operatia a fost realizata cu success!"
          this.utilityService.openModal('myCustom');
          this.message="";
        },
        err => {
          if (err.status == 400) {

          }
        });

    }

  }

}
