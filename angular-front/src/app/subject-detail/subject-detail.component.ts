import { Component, OnInit } from '@angular/core';
import {MaterialService} from "../service/material.service";
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../course.service";
import {Subject} from "../domain/subject";

@Component({
  selector: 'app-subject-detail',
  templateUrl: './subject-detail.component.html',
  styleUrls: ['./subject-detail.component.css']
})
export class SubjectDetailComponent implements OnInit {

  public subject:Subject;

  constructor(private materialService: MaterialService, private route: ActivatedRoute,private courseService: CourseService,  private materialSevice: MaterialService ) { }

  ngOnInit() {
    this.getSubject();
  }

  getSubject() {

    const id = +this.route.snapshot.paramMap.get('id');
    const idSubject = +this.route.snapshot.paramMap.get('idSubject');
    this.courseService.getSubject(id, idSubject).subscribe(subject=> {
      this.subject = subject;
    });
    console.log("kjhkkjds");
  }

  downloadFile(idMaterial: number, titleMaterial:string): void {
    var file:Blob
    this.materialSevice.getFileWithId(idMaterial, titleMaterial).subscribe(url=>window.open(url));
  }
}
