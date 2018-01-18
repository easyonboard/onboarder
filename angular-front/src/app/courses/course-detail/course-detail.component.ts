import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../../course.service";
import {Course} from "../../domain/course";
import {RootConst} from "../../util/RootConst";
import {DOCUMENT} from "@angular/common";
import {UtilityService} from "../../utility.service";
import {MaterialService} from "../../service/material.service";
import {UserDTO} from "../../domain/user";
import {Subject} from "rxjs/Subject";
import {Observable} from "rxjs/Observable";
import {UserService} from "../../user.service";
import {debounceTime, switchMap, distinctUntilChanged} from "rxjs/operators";

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})

export class CourseDetailComponent implements OnInit {
  public course: Course;
  public rootConst: RootConst;
  private fragment: string;
  private isEnrolled: Boolean;
  public isInEditingMode: Boolean;
  private modalMessage: string;
  private errorMessage: string;
  private courseFound: boolean;
  filteredOptions$: Observable<string[]>;

  private searchTerms = new Subject<string>();

  constructor(private route: ActivatedRoute, private userService: UserService, private utilityService: UtilityService, private courseService: CourseService, private materialSevice: MaterialService, @Inject(DOCUMENT) private document: any) {
  }

  getCourse(): void {

    const id = +this.route.snapshot.paramMap.get('id');
    this.route.fragment.subscribe(fragment => {
      this.fragment = fragment;
    });

    this.courseService.getCourse(id).subscribe(course => {
        this.course = course;
        this.courseFound = true;
      },
      err => {
        if (err.status == 400) {
          this.courseFound = false;
          this.modalMessage = "Course not found!";
          this.utilityService.openModal('editSuccessful');
        }
      });


    var courseList = this.courseService.getCourse(id);
    courseList.subscribe(course => {
      this.course = course;
    });

  }

  openModal(id: string) {
    this.utilityService.openModal(id);
  }

  search(term: string): void {
    this.searchTerms.next(term);
  }

  ngOnInit() {
    this.errorMessage = "";
    this.isInEditingMode = false;
    this.rootConst = new RootConst();

    this.getCourse();

    this.filteredOptions$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(100),

      // ignore new term if same as previous term
      distinctUntilChanged(),
      // switch to new sear=ch observable each time the term changes
      switchMap((term: string) => this.userService.getAllUserEmails(term)));
    this.isUserEnrollOnThisCourse();



  }


  isUserEnrollOnThisCourse(): any {
    const idCourse = +this.route.snapshot.paramMap.get('id');
    const username = localStorage.getItem("userLogged");
    this.courseService.isUserEnrollOnThisCourse(idCourse, username).subscribe(value => this.isEnrolled = value);
  }

  enrollUserToCourse(): any {
    if (confirm("Are you sure you want to enroll on this course? ")) {
      const username = localStorage.getItem("userLogged");
      const idCourse = +this.route.snapshot.paramMap.get('id');
      this.courseService.enrollUserToCourse(idCourse, username).subscribe();
      this.isEnrolled = true;
    }

  }



  unenrollUserFromCourse(): any {
    if (confirm("Are you sure you want to unenroll from this course? ")) {
      const username = localStorage.getItem("userLogged");
      const idCourse = +this.route.snapshot.paramMap.get('id');
      this.courseService.unenrollUserFromCourse(idCourse, username).subscribe();
      this.isEnrolled = false;
    }

  }



  selectSection(id: string) {
    var allElements = this.document.getElementsByClassName("nav-link js-scroll-trigger");

    for (let pos = 0; pos < allElements.length; pos++) {
      allElements[pos].style.setProperty("background-color", "#dddddd")
      allElements[pos].style.setProperty("color", "#000000");
    }


    var selectedElem = this.document.getElementsByName(id)[0];
    selectedElem.style.setProperty("background-color", "rgba(132, 20, 57, 0.9)");
    selectedElem.style.setProperty("color", "#F0F8FF");
  }


  editCourseMode() {
    this.isInEditingMode = true;


  }

  closeModal(id: string) {
    this.utilityService.closeModal(id);
  }

  editCourse(titleEdited: string, overviewEdited: string) {
    overviewEdited = overviewEdited.trim();
    titleEdited = titleEdited.trim();
    this.courseService.editCourse(this.course.idCourse, titleEdited, overviewEdited).subscribe(res => {
        this.modalMessage = "Operatia a fost realizata cu success!";
        this.openModal('editSuccessful');
        this.exitEditingMode();
        this.getCourse();
        this.isInEditingMode = false;

      },
      err => {
        this.errorMessage = err.error.message;


      });
  }

  public exitEditingMode(): void {
    this.isInEditingMode = false;
    this.errorMessage = "";

  }

  deleteContactPerson(contactPerson: UserDTO){

    this.courseService.deleteContactPerson(contactPerson, this.course).subscribe();
  }

  deleteOwnerPerson(contactPerson: UserDTO){

    this.courseService.deleteOwnerPerson(contactPerson, this.course).subscribe();
  }


  deleteSubjectFromCourse(subject: number){

    this.courseService.deleteSubjectFromCourse(subject, this.course).subscribe();
  }

  selectedEmail(email: string){
    this.courseService.addContactPerson(email, this.course).subscribe(res=>{this.getCourse();});
  }
  selectedEmailForOwner(email : string){
    this.courseService.addOwnerPerson(email, this.course).subscribe(res=>{this.getCourse();});
  }

}
