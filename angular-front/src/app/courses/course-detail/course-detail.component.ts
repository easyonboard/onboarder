import {Component,  OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {CourseService} from "../../course.service";
import {Course} from "../../course";
import {ConfirmOptions, Position} from 'angular2-bootstrap-confirm';
import {RootConst} from "../../util/RootConst";

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

  constructor(private route: ActivatedRoute, private courseService: CourseService) {
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

  isUserEnrollOnThisCourse(): any {
    const idCourse = +this.route.snapshot.paramMap.get('id');
    const username = localStorage.getItem("userLogged");
    this.courseService.isUserEnrollOnThisCourse(idCourse, username).subscribe(value => this.isEnrolled = value);
  }

  enrollUserToCourse(): any {
    if (confirm("Are you sure you want to enroll on this course? ")) {
      const username = localStorage.getItem("userLogged");
      const idCourse = +this.route.snapshot.paramMap.get('id');
      this.courseService.enrollUserToCourse(idCourse,username).subscribe();
      this.isEnrolled = true;
    }

  }


  unenrollUserFromCourse():any {
    if (confirm("Are you sure you want to unenroll from this course? ")) {
      const username = localStorage.getItem("userLogged");
      const idCourse = +this.route.snapshot.paramMap.get('id');
      this.courseService.unenrollUserFromCourse(idCourse,username).subscribe();
      this.isEnrolled = false;
    }

  }
}
