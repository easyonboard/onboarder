import {RootConst} from '../../util/RootConst';
import {UserDTO} from '../../domain/user';
import {Component, OnInit} from '@angular/core';
import {CourseService} from '../../service/course.service';
import {Course} from '../../domain/course';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-dialog-enrolled-courses-for-user',
  templateUrl: './dialog-enrolled-courses-for-user.html',
})
export class DialogEnrolledCoursesForUserComponent implements OnInit {
  public enrolledCourses: Course[];
  private rootConst: RootConst;
  private user: UserDTO;
  public progresses: Map<Course, Number>;

  constructor(private courseService: CourseService, private userService: UserService) {
    this.progresses = new Map<Course, Number>();
    this.rootConst = new RootConst();
  }

  getEnrolledCoursesForUser() {
    const username = localStorage.getItem('userLogged');
    if (username != null && username !== '') {
      this.courseService.getEnrolledCoursesForUser(username).subscribe(courses => {
        this.enrolledCourses = courses;
        this.enrolledCourses.forEach(c => this.userService.getProgress(c, this.user).subscribe(progress => {
          this.progresses.set(c, progress);
        }));
      });
    }
  }

  openPageCourseDetails(courseId: number) {
    window.location.href = this.rootConst.FRONT_DETAILED_COURSE + '/' + courseId;
  }


  ngOnInit(): void {
    this.user = new UserDTO();
    this.user.username = localStorage.getItem('userLogged');
    this.getEnrolledCoursesForUser();
  }
}


