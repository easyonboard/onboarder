import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../../course.service";
import {Course} from "../../domain/course";
import {RootConst} from "../../util/RootConst";
import {DOCUMENT} from "@angular/common";
import {UtilityService} from "../../utility.service";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  public course:Course;
  public rootConst:RootConst;
  private fragment:string;
  private isEnrolled:Boolean;
  public isInEditingMode:Boolean;
  private modalMessage:string;
  private errorMessage:string;
  private courseFound:boolean;

  constructor(private route:ActivatedRoute, private utilityService: UtilityService, private courseService:CourseService, @Inject(DOCUMENT) private document:any) {
  }

  getCourse():any {

    const id = +this.route.snapshot.paramMap.get('id');
    this.route.fragment.subscribe(fragment => {
      this.fragment = fragment;
    });
   this.courseService.getCourse(id).subscribe(course=> {
      this.course = course;
     this.courseFound=true;
   },
      err=> {
        if (err.status == 400) {
          this.errorMessage = err.error.message;
          this.courseFound=false;
          this.modalMessage="Course not found!";
          this.utilityService.openModal('myCustom');
        }
      });

  }

  ngOnInit() {
    this.errorMessage="";
    this.isInEditingMode = false;
    this.getCourse();
    this.isUserEnrollOnThisCourse();
    this.rootConst = new RootConst();


  }

  isUserEnrollOnThisCourse():any {
    const idCourse = +this.route.snapshot.paramMap.get('id');
    const username = localStorage.getItem("userLogged");
    this.courseService.isUserEnrollOnThisCourse(idCourse, username).subscribe(value => this.isEnrolled = value);
  }

  enrollUserToCourse():any {
    if (confirm("Are you sure you want to enroll on this course? ")) {
      const username = localStorage.getItem("userLogged");
      const idCourse = +this.route.snapshot.paramMap.get('id');
      this.courseService.enrollUserToCourse(idCourse, username).subscribe();
      this.isEnrolled = true;
    }

  }


  unenrollUserFromCourse():any {
    if (confirm("Are you sure you want to unenroll from this course? ")) {
      const username = localStorage.getItem("userLogged");
      const idCourse = +this.route.snapshot.paramMap.get('id');
      this.courseService.unenrollUserFromCourse(idCourse, username).subscribe();
      this.isEnrolled = false;
    }

  }

  selectSection(id:string) {
    debugger

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
    var overviewTextBox = this.document.getElementById('overviewTextBox');
    overviewTextBox.style.visibility = 'visible';
    var titleEdited = this.document.getElementById('titleEdited');
    titleEdited.style.visibility = 'visible';

  }

  closeModal(id:string){
    this.utilityService.closeModal(id);
  }
  editCourse( titleEdited:string, overviewEdited:string) {
    overviewEdited = overviewEdited.trim();
    titleEdited = titleEdited.trim();
    this.courseService.editCourse(this.course.idCourse, titleEdited, overviewEdited).subscribe(res=>{
      this.modalMessage="Operatia a fost realizata cu success!";
      this.utilityService.openModal('myCustom');
      this.exitEditingMode();
      this.getCourse();

    },
    err=>{
      this.errorMessage=err.error.message;


    });

  }

  public exitEditingMode():void {
    this.isInEditingMode = false;
    this.errorMessage="";
    var overviewTextBox = this.document.getElementById('overviewTextBox');
    overviewTextBox.style.visibility = 'hidden';
    var titleEditedInput = this.document.getElementById('titleEdited');
    titleEditedInput.style.visibility = 'hidden';

  }

}
