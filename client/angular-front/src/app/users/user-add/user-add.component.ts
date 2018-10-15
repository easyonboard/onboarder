import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialog, MatSelectChange, MatSnackBar} from '@angular/material';

import {RoleType, Role} from '../../domain/role';
import {User} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {UserInfoFormularComponent} from '../user-info-formular/user-info-formular.component';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserAddComponent implements OnInit {
  private MSG_MAIL = '@msg.group';
  public firstName = '';
  public lastName = '';
  public roleType = RoleType;

  public selectedRole: RoleType;
  public user = new User();

  public roles = Object.keys(RoleType);

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  constructor(private userService: UserService, public snackBar: MatSnackBar, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    // this.user.msgMail = this.MSG_MAIL;
  }

  addUser(): void {
    this.user.name = this.firstName.trim() + ' ' + this.lastName.trim();

    let unique: boolean;
    try {
      this.checkUserConstraints();

      this.userService.checkUnicity(this.user.username, this.user.msgMail).subscribe(
        value1 => {
          unique = value1;

          if (unique === true) {
            this.setUserInfosFields();
            this.userService.addUser(this.user, this.selectedRole).subscribe(
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
    if (!this.childUserInfoFormularComponent.user.location ||
      this.childUserInfoFormularComponent.user.location.locationName === '') {
      addUserErrorMessage += 'Please choose a location for the new user.\n';
    }
    if (!this.childUserInfoFormularComponent.user.startDate) {
      addUserErrorMessage += 'You must specify the starting date!\n';
    }
    if (!this.selectedRole) {
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

  selectRoleValue(event: MatSelectChange) {
    this.selectedRole = event.value;
  }

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }

  private setUserInfosFields() {
    console.log(this.childUserInfoFormularComponent.user);
    this.user.mate = this.childUserInfoFormularComponent.user.mate;
    this.user.department = this.childUserInfoFormularComponent.user.department;
    this.user.floor = this.childUserInfoFormularComponent.user.floor;
    this.user.location = this.childUserInfoFormularComponent.user.location;
    this.user.project = this.childUserInfoFormularComponent.user.project;
    this.user.startDate = this.childUserInfoFormularComponent.user.startDate;
    this.user.team = this.childUserInfoFormularComponent.user.team;
    console.log(this.user);

  }
}
