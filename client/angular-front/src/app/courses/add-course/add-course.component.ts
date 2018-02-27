import {Component, Inject, OnInit} from '@angular/core';
import {MaterialType} from "../../domain/materialType";
import {DOCUMENT} from "@angular/common";
import {Material} from "../../domain/material";
import {MaterialService} from "../../service/material.service";
import {RootConst} from "../../util/RootConst";
import 'rxjs/Rx';
import {UtilityService} from "../../service/utility.service";
import {Subject} from "../../domain/subject";
import {Course} from "../../domain/course";
import {CourseService} from "../../service/course.service";
import 'rxjs/add/observable/throw';
import 'rxjs/add/operator/catch';
import {SubjectService} from "../../service/subject.service";
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

  public onViewSubject: boolean;

  public usersOptions: IMultiSelectOption[];
  public ownersIds: number[]
  public contactPersonsIds: number[]

  constructor(private materialService: MaterialService, @Inject(DOCUMENT) private document: any, private utilityService: UtilityService, private materialSevice: MaterialService, private subjectService: SubjectService, private courseService: CourseService, private userService: UserService) {
    this.course = new Course();
    this.subject = new Subject();
    this.materialsForCurrentSubject = new Array<Material>();
    this.subjects = new Array<Subject>();
    this.material = new Material();
    this.material.materialType = this.materialTypeLink
    this.saved = false;
    this.subjectIndex = 0;

    this.onViewSubject = false;

    this.ownersIds = []
    this.contactPersonsIds = []

    var userArrayObjects: Array<UserDTO> = new Array<UserDTO>();
    this.userService.getAllUsers().subscribe(us => {
      userArrayObjects = userArrayObjects.concat(us);
      this.usersOptions = [];
      userArrayObjects.forEach(u => this.usersOptions.push({id: u.idUser, name: u.name + ", email:  " + u.email}))
      this.contactPersonsIds.push(Number(localStorage.getItem("userLoggedId")));
      this.ownersIds.push(Number(localStorage.getItem("userLoggedId")));
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

    this.materialService.addMaterial(this.material, this.file, this.subject.idSubject);
    this.materialsForCurrentSubject = new Array<Material>();


    this.file = null;
    var fileInput = <HTMLInputElement>document.getElementById("file");
    fileInput.innerHTML = null;
    this.material = new Material();
    this.material.materialType = this.materialTypeLink

  }

  closeAddMaterialModal(): void {
    this.file = null;
    this.material = new Material();
    var fileInput = <HTMLInputElement>document.getElementById("file");
    fileInput.innerHTML = '';

  }

  downloadFile(i: number): void {
    this.materialService.getFileWithId(i);
  }

  newSubject(): void {
    this.onViewSubject = false;
    this.subject = new Subject()
    this.subjectIndex++;
    this.materialsForCurrentSubject = new Array<Material>();
  }

  setMaterialTypeToLink(): void {
    (<HTMLSelectElement>document.getElementById("selectedMaterialType")).selectedIndex = 0;
  }

  addSubject(): void {
    this.subjectService.addSubject(this.subject, this.course).subscribe(subject => {
      this.subject = subject;
      this.subjects.push(this.subject)
    }, err => {
      alert(err.error.message)
    });
  }


  getSubjectById(pos: number) {
    this.onViewSubject = true;
    this.subject = this.subjects[pos]
    this.materialService.getMaterialsFromSubjectId(this.subject.idSubject).subscribe(materials => {
      this.materialsForCurrentSubject = materials;
      console.log(materials)
    })
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
        if(this.subjects.length<1) {
          alert("Add at least one subject!");
          break;
        }
        // if(this.materialsForCurrentSubject.length<1)
        // {
        //   alert("Add at least one material!");
        //   break;
        //
        // }
        this.currentStep = "three"
        break;
    }
  }


  ngOnInit() {
    this.currentStep = "one";
  }

  closeSubjectModal() {
    this.materialsForCurrentSubject= new Array<Material>();
  }
}
