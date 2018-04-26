import {Component, ElementRef} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {RootConst} from './util/RootConst';
import {UserService} from './service/user.service';
import {UtilityService} from './service/utility.service';
import {UserDTO} from './domain/user';
import {MatDialog} from '@angular/material';
import {UserAddComponent} from './users/user-add/user-add.component';
import {CommonComponentsService} from './common/common-components.service';
import {DialogEnrolledCoursesForUserComponent} from './common/DialogEnrolledCoursesForUser/dialog-enrolled-courses-for-user.component';
import {DialogNewEmployeeComponent} from './common/DialogNewEmployee/dialog-new-employee.component';
import {UsersInDepartmentListComponent} from './users/users-in-department-list/users-in-department-list.component';
import {DialogDeleteUsersComponent} from './common/DialogDeleteUsers/dialog-delete-users.component';


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
              private utilityService: UtilityService, private userService: UserService, private dialog: MatDialog,
              private commonComponent: CommonComponentsService) {
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


  closeModal(id: string) {
    this.utilityService.closeModal(id);
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
    } else {
      return false;
    }
  }

  newUserPopUp(): boolean {
    this.role = localStorage.getItem('userRole');
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_HR' || this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN') {
      return true;
    } else {
      return false;
    }
  }

  usersByDepartment(): boolean {
    this.role = localStorage.getItem('userRole');
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN') {
      return true;
    } else {
      return false;
    }

  }

  modalDeleteUserPermission(): boolean {
    this.role = localStorage.getItem('userRole');
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_HR' || this.role === 'ROLE_ADMIN') {
      return true;
    } else {
      return false;
    }


  }

  redirectToLoginPage(): void {
    location.replace(this.rootConst.FRONT_LOGIN_PAGE);
  }

  openDialog() {
    this.dialog.open(DialogEnrolledCoursesForUserComponent, {
      height: '650px',
      width: '900px'
    });
  }

  openModalNewEmployee() {
    this.dialog.open(DialogNewEmployeeComponent, {
      height: '650px',
      width: '900px',
    });
  }

  openModalAddNewUser() {
    this.dialog.open(UserAddComponent, {
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

  isBuddy(): boolean {
    return localStorage.getItem('userRole') === 'ROLE_BUDDY';
  }

  openToDoListForBuddy() {
    this.commonComponent.openDialogWithToDOListForBuddy();
  }

  openModalEmployeesInDepartment() {
    this.dialog.open(UsersInDepartmentListComponent, {
      height: '650px',
      width: '900px',
    });
  }

  modalDeleteUser() {
    this.dialog.open(DialogDeleteUsersComponent, {
      height: '650px',
      width: '900px',
    });
  }

  openEditProfileDialog() {
    this.commonComponent.openEditProfileDialog();
  }
}
