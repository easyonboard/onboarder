import {AfterContentInit, Component, Inject, Input, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatSelectChange, MatSnackBar} from '@angular/material';

import {RoleType} from '../../domain/role';
import {User} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {Subject} from 'rxjs/Subject';
import {Location} from '../../domain/location';
import {Department} from '../../domain/Department';
import {Observable} from 'rxjs/Observable';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {DepartmentService} from '../../service/department.service';
import {LocationService} from '../../service/location.service';

@Component({
  selector: 'app-user-add-update',
  templateUrl: './user-add-update.component.html',
  styleUrls: ['./user-add-update.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserAddComponent implements OnInit, OnDestroy {

  @Input()
  show = true;
  public user = new User();

  private MSG_MAIL = '@msg.group';
  public firstName = '';
  public lastName = '';
  public roleType = RoleType;
  public roles = Object.keys(RoleType);
  public today: Date;
  public locations: Location[];
  public departments: Department[];
  public selectedLocation: Location;

  public users$: Observable<User[]>;
  private searchTerms = new Subject<string>();


  constructor(@Inject(MAT_DIALOG_DATA) protected existingUser: User,
              private userService: UserService,
              public snackBar: MatSnackBar,
              private dialog: MatDialog,
              private departemntService: DepartmentService,
              private locationService: LocationService) {
  }

  ngOnInit(): void {
    if (typeof this.existingUser !== 'undefined' && this.existingUser !== null) {
      this.user = this.existingUser;
      this.firstName = this.user.name.split(' ')[0];
      this.lastName = this.user.name.split(' ')[1];
    }
    // this.user.msgMail = this.MSG_MAIL;
    this.today = new Date(Date.now());
    this.getDepartments();
    this.getLocations();

    this.searchAsyncUserByName();

  }


  ngOnDestroy(): void {
    this.existingUser = null;
    this.user = null;
  }

  private searchAsyncUserByName() {
    this.users$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.userService.searchUsers(term))
    );
  }


  addUser(): void {
    this.createNameForUser();
    try {
      this.checkUserConstraints();
      this.userService.checkUnicity(this.user.username, this.user.msgMail).subscribe(
        unique => {
          if (unique === true) {
            this.userService.addUser(this.user).subscribe(
              value2 => {
                this.snackBarMessagePopup('Succes! You just add a new employee!', 'Close');
                this.dialog.closeAll();
              },
              error => {
                this.snackBarMessagePopup(error.error.message, 'Close');
              }
            );
          }
        },
        error => {
          this.snackBarMessagePopup(error.error.message, 'Close');
        }
      );
    } catch (e) {
      if (e instanceof Error) {
        this.snackBarMessagePopup(e.message, 'Close');
      }
    }
  }

  private createNameForUser() {
    this.user.name = this.firstName.trim() + ' ' + this.lastName.trim();
    alert(this.user.name);
  }

  updateUser() {
    this.createNameForUser();
    try {
      this.checkUserConstraints();
      this.userService.updateUser(this.user).subscribe(
        value2 => {
          this.snackBarMessagePopup('Succes! You just updated info for employee ' + this.user.name + ' !', 'Close');
          this.dialog.closeAll();
        },
        error => {
          this.snackBarMessagePopup(error.error.message, 'Close');
        }
      );
    } catch (e) {
      if (e instanceof Error) {
        this.snackBarMessagePopup(e.message, 'Close');
      }
    }
  }


  selectRoleValue(event: MatSelectChange) {
    this.user.role = event.value;
  }


  selectDepartmentValue(event: MatSelectChange) {
    this.user.department = event.value;
  }

  getDate(): Date {
    return new Date(this.user.startDate);
  }

  selectLocationValue(event: MatSelectChange) {
    this.user.location = event.value;
  }

  search(term: string): void {
    this.searchTerms.next(term);
    this.show = true;
  }

  getUserMate(): String {
    return typeof this.user.mate !== 'undefined' ? this.user.mate.name : '';
  }


  private snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }

  private getDepartments() {
    this.departemntService.getAllDepartments().subscribe(resp => {
      this.departments = resp;
      if (typeof this.user.department !== 'undefined') {
        this.user.department = this.departments.find(departemnt => departemnt.idDepartment === this.user.department.idDepartment);
      }
    }, error => this.snackBarMessagePopup(error.error.message, 'Close'));
  }

  private getLocations() {
    this.locationService.getLocations().subscribe(resp => {
      this.locations = resp;
      if (typeof this.user.location !== 'undefined') {
        this.user.location = this.locations.find(location => location.idLocation === this.user.location.idLocation);
      }
    }, error => this.snackBarMessagePopup(error.error.message, 'Close'));
  }

  private checkUserConstraints() {
    let addUserErrorMessage = '';
    if (this.user.username === '') {
      addUserErrorMessage += 'You must give the user\'s first and last name.\n';
    } else {
      if (this.firstName === '') {
        addUserErrorMessage += 'You must give the user\'s first name.\n';
      }
      if (this.lastName === '') {
        addUserErrorMessage += 'You must give the user\'s last name.\n';
      }
    }
    if (this.user.msgMail === '') {
      addUserErrorMessage += 'You must give the user\'s msg E-mail.\n';
    }
    if (!this.user.location ||
      this.user.location.locationName === '') {
      addUserErrorMessage += 'Please choose a location for the new user.\n';
    }
    if (!this.user.startDate) {
      addUserErrorMessage += 'You must specify the starting date!\n';
    }
    if (!this.user.role) {
      addUserErrorMessage += 'You must give the user\'s role.\n';
    }
    if (this.user.email === '') {
      addUserErrorMessage += 'You must give the user\'s personal e-mail.\n';
    }
    if (this.user.msgMail === '' || this.user.msgMail === '@msg.group') {
      addUserErrorMessage += 'You must give the user\'s .msg e-mail.\n';
    }

    if (addUserErrorMessage !== '') {
      throw new Error(addUserErrorMessage);
    }
  }
}
