import {Component, ElementRef, Inject, OnInit, Optional} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {RootConst} from './util/RootConst';
import {UserService} from './service/user.service';
import {UtilityService} from './service/utility.service';
import {UserDTO} from './domain/user';
import {MAT_DIALOG_DATA, MatDialog} from '@angular/material';
import {Course} from './domain/course';
import {CourseService} from './service/course.service';
import {UserInformationDTO} from './domain/userinformation';
import {UserInformationService} from './service/user-information.service';
import {CheckListProperties} from "./util/CheckListProperties";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'
  ]
})

export class AppComponent {
  private rootConst: RootConst;
  public message: string;
  public successMessage: string;
  public username: String;

  constructor(private location: Location, private router: Router, private elemRef: ElementRef,
              private utilityService: UtilityService, private userService: UserService, private dialog: MatDialog) {
    this.rootConst = new RootConst();
    this.message = '';
    this.successMessage = '';
  }

  logout(): void {
    if (confirm('Do you really want to logout?')) {
      localStorage.removeItem('userLogged');
      this.redirectToLoginPage();
    }
  }

  redirectToAddCourse(): void {
    location.replace(this.rootConst.FRONT_ADD_COURSE);
  }

  openModal(id: string) {
    this.utilityService.openModal(id);
  }

  closeModal(id: string) {
    this.utilityService.closeModal(id);
  }

  updateUser(name: string, email: string, password: string, passwordII: string): void {
    name = name.trim();
    email = email.trim();
    password = password.trim();
    passwordII = passwordII.trim();


    if (password !== '' && (password !== passwordII || password.length < 6)) {
      this.message = 'Password not matching or does not have 6 characters';
      return;
    } else {
      let username = localStorage.getItem('userLogged');

      if (name === '') {
        name = null;
      }
      if (email === '') {
        email = null;
      }
      if (password === '') {
        password = null;
      }
      this.userService.updateUser({name, username, email, password} as UserDTO).subscribe(
        res => {
          this.utilityService.closeModal('myModal');
          this.successMessage = 'Operatia a fost realizata cu success!';
          this.utilityService.openModal('myCustom');
          this.message = '';
        },
        err => {
          this.message = err.error.message;
        });

    }

  }

  userIsLogged(): boolean {
    this.username = localStorage.getItem('userLogged');
    if (this.username !== null) {
      return true;
    }
    return false;
  }

  redirectToLoginPage(): void {
    location.replace(this.rootConst.FRONT_LOGIN_PAGE);
  }

  openDialog() {
    const dialogRef = this.dialog.open(DialogEnrolledCoursesForUser, {
      height: '650px',
      width: '900px'
    });
  }

  openModalNewEmployee() {
    this.dialog.open(DialogNewEmployees, {
      height: '650px',
      width: '900px',
    });
  }

  redirectToCoursePage() {
    location.replace(this.rootConst.FRONT_COURSES_PAGE);
  }

  redirectToGeneralInfosPage() {
    location.replace(this.rootConst.FRONT_INFOS_PAGE);
  }
}

@Component({
  selector: 'app-dialog-enrolled-courses-for-user',
  templateUrl: './app.dialog-enrolled-courses-for-user.html',
})
export class DialogEnrolledCoursesForUser implements OnInit {
  public enrolledCourses: Course[];
  public str = 'asta e';
  private rootConst: RootConst;
  private user: UserDTO;
  public progresses: Map<Course, Number>;

  constructor(private courseService: CourseService, private userService: UserService) {
    this.progresses = new Map<Course, Number>();
    this.rootConst = new RootConst();
  }

  getEnrolledCoursesForUser() {
    let username = localStorage.getItem('userLogged');
    if (username != null && username !== '') {
      this.courseService.getEnrolledCoursesForUser(username).subscribe(courses => {
        this.enrolledCourses = courses;
        this.enrolledCourses.forEach(c => this.userService.getProgress(c, this.user).subscribe(progress => {
          this.progresses.set(c, progress);
          console.log(this.progresses);
        }));
      });

    }
  }

  openPageCourseDetails(courseId: number) {
    window.location.href = this.rootConst.FRONT_DETAILED_COURSE + '/' + courseId;
  }

  getProgress(course: Course): any {
    this.userService.getProgress(course, this.user).mapTo(progress => {
      return progress;
    });
  }

  ngOnInit(): void {
    this.user = new UserDTO();
    this.user.username = localStorage.getItem('userLogged');
    this.getEnrolledCoursesForUser();
  }
}

@Component({
  selector: 'app-dialog-new-employees',
  templateUrl: './app.dialog-new-employees.html',
})
export class DialogNewEmployees implements OnInit {
  private mailSent: boolean;
  public newEmployees: UserInformationDTO[];

  constructor(private userInformationService: UserInformationService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.mailSent = false;
    this.newEmployees = [];

    this.userInformationService.getNewUsers().subscribe(newEmployees => {
      this.newEmployees = newEmployees;
      console.log(this.newEmployees);
    });

  }

  openFormular() {

    // metoda care ar pputea fi folosita pentru a adauga informatiile suplimentare despre user-ul nou
  }

  openCheckList(user: UserDTO) {
    console.log(user);
    this.dialog.open(DialogCheckListUser, {
      height: '650px',
      width: '900px',
      data: user
    });
  }

}

@Component({
  selector: 'app-dialog-check-list-user',
  templateUrl: './app.dialog-checkList.html',
})
export class DialogCheckListUser implements OnInit {
  private dialogTitle: string;
  private checkList: Map<string, boolean>;
  private checkListProperties: CheckListProperties;


  constructor(@Optional() @Inject(MAT_DIALOG_DATA) private user: UserDTO, private userService: UserService) {
  }

  ngOnInit() {
    this.dialogTitle = 'Check list for ' + this.user.name;
    this.checkList = new Map<string, boolean>();
    this.checkListProperties = new CheckListProperties();
    this.userService.getCheckListForUser(this.user).subscribe(
      data => {

        Object.keys(data).forEach(key => {
          this.checkList.set(key, data[key]);

        });
      });


    console.log(this.checkList);
  }

  onCheck(key: string) {
    console.log(key);
    this.checkList.set(key, !this.checkList.get(key));
    console.log(this.checkList);

  }

  saveStatus() {
    this.userService.saveCheckList(this.user, this.checkList);
  }

}
