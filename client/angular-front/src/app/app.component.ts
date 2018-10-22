import {Component, ElementRef, ViewEncapsulation} from '@angular/core';
import {Location} from '@angular/common';
import {ActivatedRoute, Router} from '@angular/router';
import {RootConst} from './util/RootConst';
import {UserService} from './service/user.service';
import {MatDialog} from '@angular/material';
import {CommonComponentsService} from './common/common-components.service';
import {UsersInDepartmentListComponent} from './users/users-in-department-list/users-in-department-list.component';
import {LocalStorageConst} from './util/LocalStorageConst';
import {UtilityService} from './service/utility.service';
import {AuthService} from './common/core-auth/auth.service';
import {TokenStorage} from './common/core-auth/token.storage';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class AppComponent {

  private rootConst: RootConst;
  public message: string;
  public successMessage: string;
  public username: String;
  public role: string;

  constructor(private location: Location,
              private router: Router,
              private elemRef: ElementRef,
              private utilityService: UtilityService,
              private userService: UserService,
              private dialog: MatDialog,
              private commonComponent: CommonComponentsService,
              private route: ActivatedRoute,
              private tokenStorage: TokenStorage) {
    this.rootConst = new RootConst();
    this.message = '';
    this.successMessage = '';
    this.username = localStorage.getItem(LocalStorageConst._USER_USERNAME);
  }

  logout(): void {
    if (confirm('Do you really want to logout?')) {
      this.tokenStorage.signOut();
      localStorage.clear();
      this.redirectToLoginPage();
    }
  }

  closeModal(id: string) {
    this.utilityService.closeModal(id);
  }

  userIsLogged(): boolean {
    if (null !== this.tokenStorage.getToken()) {
      return true;
    }
    return false;
  }

  newEmployeesPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_ADMIN' || this.role === 'ROLE_ABTEILUNGSLEITER') {
      return true;
    } else {
      return false;
    }
  }

  addUserPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_HR' || this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN') {
      return true;
    } else {
      return false;
    }
  }

  viewUsersByDepartmentPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN') {
      return true;
    } else {
      return false;
    }
  }

  deleteUserPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    if (this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN') {
      return true;
    } else {
      return false;
    }
  }

  redirectToLoginPage(): void {
    this.router.navigate([this.rootConst.FRONT_LOGIN_PAGE]);
  }

  openModalNewEmployee() {
    this.commonComponent.openModalNewEmployee();
  }

  openModalAddNewUser() {
    this.commonComponent.openModalAddNewUser();
  }

  redirectToInfoPage() {
    this.router.navigate([this.rootConst.FRONT_INFOS_PAGE]);
  }

  redirectToGeneralInfosPage() {
    this.router.navigate([this.rootConst.FRONT_INFOS_PAGE]);
  }

  redirectToTutorialsPage() {
    this.router.navigate([this.rootConst.FRONT_TUTORIALS_PAGE]);
    // location.replace(this.rootConst.FRONT_TUTORIALS_PAGE);
  }


  isBuddy(): boolean {
    return localStorage.getItem(LocalStorageConst._USER_ROLE) === 'ROLE_BUDDY';
  }

  openToDoListForBuddy() {
    this.commonComponent.openDialogWithToDOListForBuddy();
  }

  openModalEmployeesInDepartment() {
    this.dialog.open(UsersInDepartmentListComponent, {
      height: '90%',
      width: '900px',
    });
  }

  modalDeleteUser() {
    this.commonComponent.modalDeleteUser();
  }


  openProfileInfoDialog() {
    this.commonComponent.openProfileInfoDialog();
  }

  userManagementPermission(): boolean {
    return this.addUserPermission() || this.newEmployeesPermission()
      || this.deleteUserPermission() || this.viewUsersByDepartmentPermission();
  }

  redirectToAddTutorialPage() {
    this.router.navigate(['/tutorials/addTutorial']);
  }

  redirectToEventsPage() {
    this.router.navigate(['/events/viewEvents']);
  }

  redirectToAddEvent() {

  }

  redirectToAddEventPage() {
    this.router.navigate(['/events/addEvent']);
  }


  isFiltered(): boolean {
    return location.href.indexOf('keyword') >= 0;
  }

  removeFilter() {
    this.router.navigate([location.pathname]);




  }
}
