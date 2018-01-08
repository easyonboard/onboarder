import {Component, Inject, OnInit} from '@angular/core';
import {MaterialType} from "../../domain/materialType";
import {DOCUMENT} from "@angular/common";
import {Material} from "../../domain/material";
import {MaterialService} from "../../service/material.service";
import {RootConst} from "../../util/RootConst";
import 'rxjs/Rx';

@Component({
  selector: 'app-add-course',
  templateUrl: './add-course.component.html',
  styleUrls: ['./add-course.component.css']
})
export class AddCourseComponent implements OnInit {
  public materialTypeLink: MaterialType.LINK
  public materialTypeFile: MaterialType.FILE
  private material: Material = new Material();
  public selectedMaterialType: string;
  private rootConst: RootConst = new RootConst();
  public addMaterialURL = this.rootConst.SERVER_ADD_MATERIAL;
  public file: File;

  constructor(private materialService: MaterialService, @Inject(DOCUMENT) private document: any) {
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


  ngOnInit() {
    <HTMLOptionElement>this.document.getElementById("valueLink").select();
  }

}
