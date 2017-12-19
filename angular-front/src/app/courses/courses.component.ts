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
  constructor(private courseService: CourseService, private location:Location, private userService: UserService) { }


  ngOnInit():void {
    this.message="";
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

  editProfile(id:string):void{
    var modal=document.getElementById('myModal');

    modal.style.display = "block";

  }
  closeModal(myModal:string ): void {

    var modal=document.getElementById('myModal');
    modal.style.display="none";

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
      this.userService.updateUser({name,username, email,password} as UserDTO).subscribe(
        res => {
          this.closeModal('myModal');
          alert("Operatia s-a realizat cu success!");
        },
        err => {
          if (err.status == 400) {

          }
        });

    }

  }

}
