import {Component, ElementRef, ViewEncapsulation, AfterViewChecked, ViewChild} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from '@angular/router';
import {RootConst} from './util/RootConst';
import {UserService} from './service/user.service';
import {UtilityService} from './service/utility.service';
import {MatDialog, MatTooltip} from '@angular/material';
import {CommonComponentsService} from './common/common-components.service';
import {UsersInDepartmentListComponent} from './users/users-in-department-list/users-in-department-list.component';
import {LocalStorageConst} from './util/LocalStorageConst';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
  encapsulation: ViewEncapsulation.None
})

export class AppComponent implements AfterViewChecked {

  private rootConst: RootConst;
  public message: string;
  public successMessage: string;
  public username: String;
  public role: string;

  public home_msg = 'Go to the home page';
  public info_msg = 'Find useful general informations';
  public show_popup_tutorial_msg = 'Show me how to use this site';
  public hide_popup_tutorial_msg = 'I know how to use this site :)';
  public tutorials_msg = 'View existing tutorials and add new ones';
  public management_msg = 'Find and update informations about users';
  public user_msg = 'Change your password';
  public events_msg = 'See the new .msg events';

  public show;

  constructor(private location: Location, private router: Router, private elemRef: ElementRef,
              private utilityService: UtilityService, private userService: UserService, private dialog: MatDialog,
              private commonComponent: CommonComponentsService) {
    this.rootConst = new RootConst();
    this.message = '';
    this.successMessage = '';

    this.show = localStorage.IS_DEMO_ENABLED;
  }

  @ViewChild('tooltipHome') tooltipHome: MatTooltip;
  @ViewChild('tooltipInfo') tooltipInfo: MatTooltip;
  @ViewChild('tooltipTutorials') tooltipTutorials: MatTooltip;
  @ViewChild('tooltipEvents') tooltipEvents: MatTooltip;
  @ViewChild('tooltipManagement') tooltipManagement: MatTooltip;
  @ViewChild('tooltipUser') tooltipUser: MatTooltip;

  ngAfterViewChecked() {
    this.show = LocalStorageConst.IS_DEMO_ENABLED;

    if (this.tooltipHome !== undefined && this.show === true) {
      this.tooltipHome.disabled = false;
    } else {
      this.tooltipHome.disabled = true;
    }
    if (this.tooltipInfo !== undefined && this.show === true) {
      this.tooltipInfo.disabled = false;
    } else {
      this.tooltipInfo.disabled = true;
    }
    if (this.tooltipTutorials !== undefined && this.show === true) {
      this.tooltipTutorials.disabled = false;
    } else {
      this.tooltipTutorials.disabled = true;
    }
    if (this.tooltipEvents !== undefined && this.show === true) {
      this.tooltipEvents.disabled = false;
    } else {
      this.tooltipEvents.disabled = true;
    }
    if (this.tooltipManagement !== undefined && this.show === true) {
      this.tooltipManagement.disabled = false;
    } else {
      this.tooltipManagement.disabled = true;
    }
    if (this.tooltipUser !== undefined && this.show === true) {
      this.tooltipUser.disabled = false;
    } else {
      this.tooltipUser.disabled = true;
    }
  }

  toggleTutorialMode(): void {
    this.show = LocalStorageConst.toggle(localStorage.getItem(LocalStorageConst._DEMO_ENABLED));
    console.log('Now it is: ' + localStorage.getItem(LocalStorageConst._DEMO_ENABLED) + ' ' + this.show);
  }

  logout(): void {
    if (confirm('Do you really want to logout?')) {
      localStorage.removeItem(LocalStorageConst._USER_LOGGED);
      localStorage.removeItem(LocalStorageConst._USER_LOGGED_ID);
      localStorage.removeItem(LocalStorageConst._USER_ROLE);
      localStorage.removeItem(LocalStorageConst._USER_FIRSTNAME);
      localStorage.removeItem(LocalStorageConst._MSG_MAIL);

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
    this.username = localStorage.getItem(LocalStorageConst._USER_FIRSTNAME);
    if (this.username !== null) {
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
    if (this.role === 'ROLE_HR' || this.role === 'ROLE_ADMIN') {
      return true;
    } else {
      return false;
    }
  }

  redirectToLoginPage(): void {
    location.replace(this.rootConst.FRONT_LOGIN_PAGE);
  }

  openDialogCoursesForUser() {
    this.commonComponent.openDialogCoursesForUser();
  }

  openModalNewEmployee() {
    this.commonComponent.openModalNewEmployee();
  }

  openModalAddNewUser() {
    this.commonComponent.openModalAddNewUser();
  }

  redirectToCoursePage() {
    location.replace(this.rootConst.FRONT_INFOS_PAGE);
  }

  redirectToGeneralInfosPage() {
    location.replace(this.rootConst.FRONT_INFOS_PAGE);
  }

  redirectToTutorialsPage() {
    location.replace(this.rootConst.FRONT_TUTORIALS_PAGE);
  }


  isBuddy(): boolean {
    return localStorage.getItem(LocalStorageConst._USER_ROLE) === 'ROLE_BUDDY';
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
    this.commonComponent.modalDeleteUser();
  }

  openEditProfileDialog() {
    this.commonComponent.openEditProfileDialog();
  }

  userManagementPermission(): boolean {
    return this.addUserPermission() || this.newEmployeesPermission()
      || this.deleteUserPermission() || this.viewUsersByDepartmentPermission();
  }

  redirectToAddTutorialPage() {
    this.router.navigate(['/tutorials/addTutorialRouterLink']);
  }

  redirectToEventsPage() {
    this.router.navigate(['/events/viewEvents']);
  }

  redirectToAddEvent() {

  }

  redirectToAddEventPage() {
    this.router.navigate(['/events/addEventRouterLink']);
  }
}
