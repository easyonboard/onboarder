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
import {JwtHelperService} from '@auth0/angular-jwt';

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
  jwtHelper: JwtHelperService = new JwtHelperService();

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
    this.username = localStorage.getItem(LocalStorageConst._USER_FIRST_NAME);
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
    return null !== this.tokenStorage.getToken();

  }

  newEmployeesPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    return this.role === 'ROLE_ADMIN' || this.role === 'ROLE_ABTEILUNGSLEITER';
  }

  addUserPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    return this.role === 'ROLE_HR' || this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN';
  }

  viewUsersByDepartmentPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    return this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN';
  }

  deleteUserPermission(): boolean {
    this.role = localStorage.getItem(LocalStorageConst._USER_ROLE);
    if (!this.userIsLogged()) {
      return false;
    }
    return this.role === 'ROLE_ABTEILUNGSLEITER' || this.role === 'ROLE_ADMIN';
  }

  redirectToLoginPage(): void {
    this.router.navigate([this.rootConst.FRONT_LOGIN_PAGE]);
  }

  openModalNewEmployee() {
    if (this.checkToken()) {
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigateByUrl('/login');
      return;
    } else {
      this.commonComponent.openModalNewEmployee();
    }
  }

  openModalAddNewUser() {
    if (this.checkToken()) {
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigateByUrl('/login');
      return;
    } else {
      this.commonComponent.openModalAddNewUser();
    }
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


  checkToken(): boolean {
    return this.jwtHelper.isTokenExpired(sessionStorage.getItem('AuthToken'));
  }


  openToDoListForBuddy() {
    if (this.checkToken()) {
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigateByUrl('/login');
      return;
    } else {
      this.commonComponent.openDialogWithToDOListForBuddy();
    }
  }

  openModalEmployeesInDepartment() {
    if (this.checkToken()) {
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigateByUrl('/login');
      return;
    } else {
    this.dialog.open(UsersInDepartmentListComponent, {
      height: '90%',
      width: '900px',
    });
  }}

  modalDeleteUser() {
    if (this.checkToken()) {
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigateByUrl('/login');
      return;
    } else {
      this.commonComponent.modalDeleteUser();
    }
  }
  openProfileInfoDialog() {
    if (this.checkToken()) {
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigateByUrl('/login');
      return;
    } else {
      this.commonComponent.openProfileInfoDialog();
    }
  }


  redirectToAddTutorialPage() {
    this.router.navigate(['/tutorials/addTutorial']);
  }

  redirectToEventsPage() {
    this.router.navigate(['/events/viewEvents']);
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
