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
  public rootConst:RootConst;
  private fragment: string;

  constructor(private route: ActivatedRoute, private courseService: CourseService, private location: Location, private router: Router){
  }

  getCourse(): void{
    const id=+this.route.snapshot.paramMap.get('id');
    this.route.fragment.subscribe(fragment => { this.fragment = fragment; });
    this.courseService.getCourse(id).subscribe(course=>this.course=course);
  }

  ngOnInit() {
    this.getCourse();
    this.rootConst= new RootConst();
  }
  goBack(): void {
    this.location.back();
  }
}
