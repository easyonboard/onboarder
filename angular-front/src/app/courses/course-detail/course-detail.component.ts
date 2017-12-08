import {Component, Inject, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../../course.service";
import {Course} from "../../course";
import { Location } from '@angular/common';
import { RootConst } from "../../util/RootConst";

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  public course: Course;
  private rootConst:RootConst= new RootConst();


  constructor(private route: ActivatedRoute, private courseService: CourseService, private location: Location, private router: Router){
  }

  getCourse(): void{
    debugger
    const id=+this.route.snapshot.paramMap.get('id');
    this.courseService.getCourse(id).subscribe(c=>this.course=c);
    console.log(this.course)
  }

  ngOnInit() {
    this.getCourse();

  }
  goBack(): void {
    this.location.back();
  }
}
