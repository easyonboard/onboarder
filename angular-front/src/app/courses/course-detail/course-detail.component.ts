import {Component, Inject, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {CourseService} from "../../course.service";
import {Course} from "../../course";
import {Location} from '@angular/common';
import {RootConst} from "../../util/RootConst";
import {Observable} from "rxjs/Observable";

@Component({
  selector: 'app-course-detail',
  templateUrl: './course-detail.component.html',
  styleUrls: ['./course-detail.component.css']
})
export class CourseDetailComponent implements OnInit {
  public course: Course;
  public rootConst: RootConst;
  private fragment: string;
  private isEnrolled: Boolean;

  constructor(private route: ActivatedRoute, private courseService: CourseService, private location: Location, private router: Router) {
  }

  getCourse(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.route.fragment.subscribe(fragment => {
      this.fragment = fragment;
    });
    this.courseService.getCourse(id).subscribe(course => this.course = course);
  }

  ngOnInit() {
    this.getCourse();
    this.isUserEnrollOnThisCourse();
    this.rootConst = new RootConst();

  }

  goBack(): void {
    this.location.replaceState("/");
  }


  isUserEnrollOnThisCourse(): any {
    const idCourse = +this.route.snapshot.paramMap.get('id');
    this.courseService.isUserEnrollOnThisCourse(idCourse).subscribe(value => this.isEnrolled = value);
  }

  enrolleUserToCourse(): any {
    if (confirm("Are you sure you want to enroll on this course? ")) {
      const idCourse = +this.route.snapshot.paramMap.get('id');
      this.courseService.enrolleUserToCourse(idCourse).subscribe();
      this.isEnrolled = true;
    }

  }


}
