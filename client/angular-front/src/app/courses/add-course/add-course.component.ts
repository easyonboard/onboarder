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
  private material: Material = new Material();
  public selectedMaterialType: string;
  private rootConst: RootConst = new RootConst();
  public file: File;
  public subjects: Array<Subject>;
  public files: Array<File>;
  private currentStep: string;
  private materialsForCurrentSubject: Array<Material>

  private noErr: Boolean;
  public saved: Boolean

  constructor(private materialService: MaterialService, @Inject(DOCUMENT) private document: any, private utilityService: UtilityService, private materialSevice: MaterialService, private subjectService: SubjectService, private courseService: CourseService) {
    this.files = new Array<File>();
    this.course = new Course();
    this.subject = new Subject();
    this.materialsForCurrentSubject = new Array<Material>();
    this.saved = false;
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
    console.log(this.material);
    this.materialService.addMaterial(this.material, this.file)
    console.log(this.file);
    this.materialsForCurrentSubject.push(this.material)
    this.files.push(this.file);
    this.file = null;
    var fileInput = <HTMLInputElement>document.getElementById("file");
    fileInput.innerHTML = null;
    this.material = new Material();
  }

  closeAddMaterialModal(): void {
    this.file = null;
    this.material = new Material();
    var fileInput = <HTMLInputElement>document.getElementById("file");
    fileInput.innerHTML = '';

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


  addCourse() {
    this.courseService.addCourse(this.course).subscribe(course => {
      this.course = course;
      this.saved = true;
      this.incStep();
      console.log(this.course)
    }, err => {
      alert(err.error.message)
    });

  }


  downloadFile(i: number): void {
    debugger
    var binaryData = [];
    binaryData.push(this.files[i]);
    var fileURL = window.URL.createObjectURL(new Blob(binaryData, {type: 'application/pdf'}))
    window.open(fileURL);
  }

  newSubject(): void {
    this.subject = new Subject()
    this.files = new Array<File>();
  }

  ngOnInit() {
    this.currentStep = "one";
  }

  setMaterialTypeToLink(): void {
    (<HTMLSelectElement>document.getElementById("selectedMaterialType")).selectedIndex = 0;
    this.printMaterialType()
  }

  addSubject(): void {
    this.subjectService.addSubject(this.subject,this.course).subscribe(subject => {
      this.subject = subject;
      console.log(this.subject)
    }, err => {
      alert(err.error.message)
    });

  }
}
