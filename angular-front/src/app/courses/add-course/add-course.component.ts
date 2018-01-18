import {Component, Inject, OnInit} from '@angular/core';
import {MaterialType} from "../../domain/materialType";
import {DOCUMENT} from "@angular/common";
import {Material} from "../../domain/material";
import {MaterialService} from "../../service/material.service";
import {RootConst} from "../../util/RootConst";
import 'rxjs/Rx';
import {UtilityService} from "../../utility.service";
import {Subject} from "../../domain/subject";
import {NgSwitch} from '@angular/common';
import {Course} from "../../domain/course";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {
  public course: Course;
  public materialTypeLink: MaterialType.LINK
  public materialTypeFile: MaterialType.FILE
  private material: Material = new Material();
  public selectedMaterialType: string;
  private rootConst: RootConst = new RootConst();
  public addMaterialURL = this.rootConst.SERVER_ADD_MATERIAL;
  public file: File;
  public subjects: Subject[];
  public selectedMaterials: Array<Material>;
  public files: Array<File>;
  private currentStep: string;
  public materialsUploadedByThisUser: Material[];

  constructor(private materialService: MaterialService, @Inject(DOCUMENT) private document: any, private utilityService: UtilityService, private materialSevice: MaterialService) {
    this.selectedMaterials = new Array<Material>();
    this.files = new Array<File>();
    this.course = new Course();
    this.allMaterialsUploadedByThisUser();

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


  createMaterial(): void {
    this.file = (<HTMLInputElement>document.getElementById("file")).files[0];
    console.log(this.material)
    console.log(this.file)
    this.materialService.addMaterial(this.material, this.file)
  }


  addMaterial(): void {
    this.file = (<HTMLInputElement>document.getElementById("file")).files[0];
    console.log(this.material);
    console.log(this.file);
    this.selectedMaterials.push(this.material);
    this.files.push(this.file);

    this.file = null;
    this.material = new Material();

    console.log(this.selectedMaterials);
    console.log(this.files);

  }

  getCurrentStep(): string {
    return this.currentStep;
  }

  incStep() {
    switch (this.currentStep) {
      case ("one"):
        this.currentStep = "two"
        break;
      case ("two"):
        this.currentStep = "three"
        break;
    }
  }

  decStep() {
    switch (this.currentStep) {
      case ("two"):
        this.currentStep = "one"
        break;
      case ("three"):
        this.currentStep = "two"
        break;
    }
  }

  allMaterialsUploadedByThisUser() {
    const username = localStorage.getItem("userLogged");
    this.materialService.allMaterialsAddedByUser(username).subscribe(m=>this.materialsUploadedByThisUser = m);

  }

  ngOnInit() {
    this.currentStep = "one";
    <HTMLOptionElement>this.document.getElementById("valueLink").select();
    console.log(this.materialsUploadedByThisUser);
  }


  downloadFile(idMaterial: number, titleMaterial: string): void {
    var file: Blob
    this.materialSevice.getFileWithId(idMaterial, titleMaterial).subscribe(url => window.open(url));
  }

  addMaterialToSelectedList(selMaterial: Material): void {
    var position = this.selectedMaterials.indexOf(selMaterial);
    if (position < 0) {
      this.selectedMaterials.push(selMaterial);
    } else {
      this.selectedMaterials.splice(position, 1);
    }
  }
}
