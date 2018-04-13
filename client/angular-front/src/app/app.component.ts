import {Component, ElementRef, Inject, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {RootConst} from './util/RootConst';
import {UserService} from './service/user.service';
import {UtilityService} from './service/utility.service';
import {UserDTO, UserInformationDTO} from './domain/user';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material';
import {Course} from './domain/course';
import {CourseService} from './service/course.service';
import {UserInformationService} from './service/user-information.service';
import {CheckListProperties} from './util/CheckListProperties';
import {UserInfoFormularComponent} from './users/user-info-formular/user-info-formular.component';
import {UserAddComponent} from './users/user-add/user-add.component';
import {TSMap} from 'typescript-map';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})

export class AppComponent {

  private rootConst: RootConst;
  public message: string;
  public successMessage: string;
  public username: String;
  public role: string;

  constructor(private location: Location, private router: Router, private elemRef: ElementRef,
              private utilityService: UtilityService, private userService: UserService, private dialog: MatDialog) {
    this.rootConst = new RootConst();
    this.message = '';
    this.successMessage = '';

  }

  logout(): void {
    if (confirm('Do you really want to logout?')) {
      localStorage.removeItem('userLogged');
      localStorage.removeItem('userLoggedId');
      localStorage.removeItem('userRole');
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

  newEmployeesPopUp(): boolean {
    this.role = localStorage.getItem('userRole');
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_ADMIN' || this.role === 'ROLE_ABTEILUNGSLEITER') {
      return true;
    }
    else {
      return false;
    }
    ;

  }

  newUserPopUp(): boolean {
    this.role = localStorage.getItem('userRole');
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_HR' || this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN')
      return true;
    else {
      return false;
    }
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

  openModalAddNewUser() {
    const addUserComponent = this.dialog.open(UserAddComponent, {
      height: '850px',
      width: '600px',
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


  ngOnInit(): void {
    this.user = new UserDTO();
    this.user.username = localStorage.getItem('userLogged');

    this.getEnrolledCoursesForUser();
  }
}

@Component({
  selector: 'app-dialog-new-employees',
  templateUrl: './app.dialog-new-employees.html'
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

  openCheckList(user: UserDTO) {
    console.log(user);
    this.dialog.open(DialogCheckListUser, {
      height: '650px',
      width: '900px',
      data: user
    });
  }

  openUserInfoModal(userInformation: UserInformationDTO) {
    this.dialog.open(UserInfoFormularComponent, {
      height: '650px',
      width: '900px',
      data: userInformation

    });
  }

}

@Component({
  selector: 'app-dialog-check-list-user',
  templateUrl: './app.dialog-checkList.html',
})
export class DialogCheckListUser implements OnInit {
  private dialogTitle: string;
  private checkList: TSMap<string, boolean>;
  private checkListProperties: CheckListProperties;


  constructor(@Inject(MAT_DIALOG_DATA) private user: UserDTO, private userService: UserService, public dialogRef: MatDialogRef<DialogCheckListUser>) {
  }

  ngOnInit() {
    this.dialogTitle = 'Check list for ' + this.user.name;
    this.checkList = new TSMap<string, boolean>();
    this.checkListProperties = new CheckListProperties();
    this.userService.getCheckListForUser(this.user).subscribe(
      data => {

        Object.keys(data).forEach(key => {
          this.checkList.set(key, data[key]);

        });
      });
  }

  get checkListKeys() {
    return Array.from(this.checkList.keys());
  }

  onCheck(key: string) {

    this.checkList.set(key, !this.checkList.get(key));

  }

  saveStatus() {

    this.userService.saveCheckList(this.user.username, this.checkList).subscribe();
  }

  closeWindow() {
    this.dialogRef.close('Closed!');
  }
}
