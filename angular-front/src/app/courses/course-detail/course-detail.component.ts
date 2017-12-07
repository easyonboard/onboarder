import {Component, Inject, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../../course.service";
import {Course} from "../../course";
import { Location } from '@angular/common';
@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  public course: Course;
  constructor(private route: ActivatedRoute, private courseService: CourseService, private location: Location, private router: Router){
  }
  showStyle: false;

  getStyle() {
    if(this.showStyle){
      return "#dce5ff";
    } else {
      return "";
    }
  }

  getCourse(): void{

    const id=+this.route.snapshot.paramMap.get('id');
    this.courseService.getCourse(id).subscribe(course=>this.course=course);
  }

  ngOnInit() {
    this.getCourse();

  }
  goBack(): void {
    this.location.back();
  }
}
