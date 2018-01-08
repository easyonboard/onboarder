import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../../course.service";
import {Course} from "../../domain/course";
import {RootConst} from "../../util/RootConst";
import {DOCUMENT} from "@angular/common";
import {UtilityService} from "../../utility.service";
import {MaterialService} from "../../service/material.service";

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
  private successMessage: string;

  constructor(private route: ActivatedRoute, private utilityService: UtilityService, private courseService: CourseService, private materialSevice:MaterialService ,@Inject(DOCUMENT) private document: any,) {
  }

  getCourse(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.route.fragment.subscribe(fragment => {
      this.fragment = fragment;
    });
    var courseList = this.courseService.getCourse(id);
    courseList.subscribe(course => {this.course = course; console.log(this.course.subjects[0].materials[3])});
  }


  ngOnInit() {
    this.isInEditingMode = false;
    this.getCourse();
    this.isUserEnrollOnThisCourse();
    this.rootConst = new RootConst();

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
    debugger

    var allElements = this.document.getElementsByClassName("nav-link js-scroll-trigger");

    for (let pos = 0; pos < allElements.length; pos++) {
      allElements[pos].style.setProperty("background-color", "#dddddd")
    }


    var selectedElem = this.document.getElementsByName(id)[0];
    selectedElem.style.setProperty("background-color", "#15b28f")
  }

  editCourseMode() {
    this.isInEditingMode = true;
    var overviewTextBox = this.document.getElementById('overviewTextBox');
    overviewTextBox.style.visibility = 'visible';
    var titleEdited = this.document.getElementById('titleEdited');
    titleEdited.style.visibility = 'visible';

  }

  closeModal(id: string) {
    this.utilityService.closeModal(id);
  }

  editCourse(titleEdited: string, overviewEdited: string) {
    overviewEdited = overviewEdited.trim();
    titleEdited = titleEdited.trim();
    this.courseService.editCourse(this.course.idCourse, titleEdited, overviewEdited).subscribe();
    this.successMessage = "Operatia a fost realizata cu success!";
    this.utilityService.openModal('myCustom');
    this.exitEditingMode();
  }

  public

  exitEditingMode(): void {
    this.isInEditingMode = false;
    var overviewTextBox = this.document.getElementById('overviewTextBox');
    overviewTextBox.style.visibility = 'hidden';
    var titleEditedInput = this.document.getElementById('titleEdited');
    titleEditedInput.style.visibility = 'hidden';
  }

  downloadFile(idMaterial: number, titleMaterial:string): void {
    var file:Blob
    this.materialSevice.getFileWithId(idMaterial, titleMaterial).subscribe();

// var b: any = material.fileMaterial;
// //A Blob() is almost a File() - it's just missing the two properties below which we will add
// b.lastModifiedDate = new Date();
// b.name = material.title;
// //
// //Cast to a File() type
// return <File>material.fileMaterial;

  }
}
