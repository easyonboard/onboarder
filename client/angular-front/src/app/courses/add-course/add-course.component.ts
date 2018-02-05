import {Component, Inject, OnInit} from '@angular/core';
import {MaterialType} from "../../domain/materialType";
import {DOCUMENT} from "@angular/common";
import {Material} from "../../domain/material";
import {MaterialService} from "../../service/material.service";
import {RootConst} from "../../util/RootConst";
import 'rxjs/Rx';
import {UtilityService} from "../../service/utility.service";
import {Subject} from "../../domain/subject";
import {NgSwitch} from '@angular/common';
import {Course} from "../../domain/course";
import {Observable} from "rxjs/Observable";
import {CourseService} from "../../service/course.service";
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import {SubjectService} from "../../service/subject.service";
import {isUndefined} from "util";
import {IMultiSelectOption} from 'angular-2-dropdown-multiselect';
import {UserService} from "../../service/user.service";
import {UserDTO} from "../../domain/user";


@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {
  public course: Course;
  public subject: Subject
  public materialTypeLink: MaterialType.LINK
  public materialTypeFile: MaterialType.FILE
  private material: Material;
  public selectedMaterialType: string;
  private rootConst: RootConst = new RootConst();
  public file: File;

  private currentStep: string;
  private materialsForCurrentSubject: Array<Material>
  public saved: Boolean
  private subjectIndex: number;

  public subjects: Array<Subject>;
  public files: Array<File>;
  private materialsForAllSubjectsFromCourse: Array<Material>;
  private filesForAllSubjectFromCourse: Array<File>;
  public firstIndexMaterialForSubject: Array<number>;
  public lastIndexMaterialForSubject: Array<number>;

  public onViewSubject: boolean;

  public usersOptions: IMultiSelectOption[];
  public ownersIds: number[]
  public contactPersonsIds:number[]

  constructor(private materialService: MaterialService, @Inject(DOCUMENT) private document: any, private utilityService: UtilityService, private materialSevice: MaterialService, private subjectService: SubjectService, private courseService: CourseService, private userService: UserService) {
    this.files = new Array<File>();
    this.course = new Course();
    this.subject = new Subject();
    this.materialsForCurrentSubject = new Array<Material>();
    this.subjects = new Array<Subject>();
    this.material = new Material();
    this.material.materialType = this.materialTypeLink
    this.saved = false;
    this.subjectIndex = 0
    this.firstIndexMaterialForSubject = new Array<number>();
    this.lastIndexMaterialForSubject = new Array<number>();
    this.onViewSubject = false;

    this.ownersIds=[]
    this.contactPersonsIds=[]

    this.materialsForAllSubjectsFromCourse = new Array<Material>();
    this.filesForAllSubjectFromCourse = new Array<File>();
    var userArrayObjects:Array<UserDTO> = new Array<UserDTO>();
    this.userService.getAllUsers().subscribe(us => {
      userArrayObjects = userArrayObjects.concat(us);
      this.usersOptions=[]
      userArrayObjects.forEach(u => this.usersOptions.push({id: u.idUser, name: u.name + ", email:  " + u.email}))
      this.contactPersonsIds.push( Number(localStorage.getItem("userLoggedId")));
      this.ownersIds.push( Number(localStorage.getItem("userLoggedId")));
    });
  }

  addCourse() {
    this.courseService.addCourse(this.course, this.ownersIds, this.contactPersonsIds).subscribe(course => {
      this.course = course;
      this.saved = true;
      this.incStep();
    }, err => {
      alert(err.error.message)
    });
  }

  printMaterialType(): void {
    var e = this.document.getElementById("selectedMaterialType");
    this.selectedMaterialType = e.options[e.selectedIndex].value;
    if (this.selectedMaterialType === "FILE") {
      var addFileDiv = this.document.getElementById("addFile");
      addFileDiv.style.visibility = 'visible';
      var linkFile = this.document.getElementById("addLink");
      linkFile.style.visibility = 'hidden';
    }
    if (this.selectedMaterialType === "LINK") {
      var addFileDiv = this.document.getElementById("addFile");
      addFileDiv.style.visibility = 'hidden';
      var linkFile = this.document.getElementById("addLink");
      linkFile.style.visibility = 'visible';
    }
  }


  addMaterial(): void {
    debugger
    this.file = (<HTMLInputElement>document.getElementById("file")).files[0];
    this.materialsForCurrentSubject.push(this.material)

    var lastIndex = this.lastIndexMaterialForSubject.length - 1;
    this.lastIndexMaterialForSubject[lastIndex] = this.lastIndexMaterialForSubject[lastIndex] + 1
    if (isUndefined(this.file)) {
      this.files.push(null);
    } else {
      this.files.push(this.file);
    }
    this.file = null;
    var fileInput = <HTMLInputElement>document.getElementById("file");
    fileInput.innerHTML = null;
    this.material = new Material();
    this.material.materialType = this.materialTypeLink
    console.log(this.materialsForCurrentSubject)
  }

  closeAddMaterialModal(): void {
    this.file = null;
    this.material = new Material();
    var fileInput = <HTMLInputElement>document.getElementById("file");
    fileInput.innerHTML = '';

  }

  downloadFile(i: number): void {
    debugger
    var binaryData = [];
    binaryData.push(this.files[i]);
    var fileURL = window.URL.createObjectURL(new Blob(binaryData, {type: 'application/pdf'}))
    window.open(fileURL);
  }

  newSubject(): void {
    this.onViewSubject = false;
    this.subject = new Subject()
    this.subjectIndex++;
    this.firstIndexMaterialForSubject.push(this.materialsForCurrentSubject.length);
    this.lastIndexMaterialForSubject.push(this.materialsForCurrentSubject.length - 1);
    this.files = new Array<File>();
    this.materialsForCurrentSubject = new Array<Material>();
  }

  setMaterialTypeToLink(): void {
    (<HTMLSelectElement>document.getElementById("selectedMaterialType")).selectedIndex = 0;
  }

  addSubject(): void {
    this.subjectService.addSubject(this.subject, this.course).subscribe(subject => {
      this.subject = subject;
      this.subjects.push(this.subject)
      this.materialService.addMaterialsToSubject(this.subject.idSubject, this.materialsForCurrentSubject, this.files);

      this.materialsForAllSubjectsFromCourse = this.materialsForAllSubjectsFromCourse.concat(this.materialsForCurrentSubject);
      this.materialsForCurrentSubject = new Array<Material>();

      this.filesForAllSubjectFromCourse = this.filesForAllSubjectFromCourse.concat(this.files);
      this.files = null;
    }, err => {
      alert(err.error.message)
    });
  }

  getSubjectById(pos: number) {
    this.onViewSubject = true;
    this.subject = this.subjects[pos]
    this.files = this.filesForAllSubjectFromCourse.slice(this.firstIndexMaterialForSubject[pos], this.lastIndexMaterialForSubject[pos] + 1);
    this.materialsForCurrentSubject = this.materialsForAllSubjectsFromCourse.slice(this.firstIndexMaterialForSubject[pos], this.lastIndexMaterialForSubject[pos] + 1)
  }

  getCurrentStep(): string {
    return this.currentStep;
  }

  incStep() {
    switch (this.currentStep) {
      case ('one'):
        this.currentStep = 'two'
        this.saved = false;
        break;
      case ("two"):
        this.currentStep = "three"
        break;
    }
  }


  ngOnInit() {
    this.currentStep = "one";
  }

}
