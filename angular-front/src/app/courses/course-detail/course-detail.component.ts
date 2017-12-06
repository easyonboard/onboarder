import {Component, Inject, OnInit} from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  constructor() {
  }
  showStyle: false;

  getStyle() {
    if(this.showStyle){
      return "#dce5ff";
    } else {
      return "";
    }
  }

  ngOnInit() {

  }

}
