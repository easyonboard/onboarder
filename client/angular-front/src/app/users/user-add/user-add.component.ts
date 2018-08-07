import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialog, MatSelectChange, MatSnackBar} from '@angular/material';

import {RoleType, RoleDTO} from '../../domain/role';
import {UserDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {UserInfoFormularComponent} from '../user-info-formular/user-info-formular.component';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserAddComponent implements OnInit {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  public firstName = '';
  public lastName = '';
  public roleType = RoleType;
  public selectedRole: RoleType;

  public user = new UserDTO();
  public roles = Object.keys(RoleType);

  constructor(private userService: UserService, public snackBar: MatSnackBar, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.user.msgMail = '@msg.group';
  }

  addUser(): void {
    this.user.username = this.firstName.trim() + this.lastName.trim();
    this.user.name = this.firstName.trim() + ' ' + this.lastName.trim();

    let unique: boolean;
    try {
      this.checkUserConstraints();

      this.userService.checkUnicity(this.user.username, this.user.msgMail).subscribe(
        value1 => {
          unique = value1;

          if (unique === true) {
            this.userService.addUser(this.user, this.selectedRole, this.childUserInfoFormularComponent.userInformation).subscribe(
                value2 => {
                    this.snackBarMessagePopup('Succes! You just add a new employee!', 'Close');
                    this.dialog.closeAll();
                },
                error => {
                  this.snackBarMessagePopup(error.error.message, 'Close');
                }
              );
          }
          // else {
          //   this.snackBarMessagePopup('Failed! Username or .msg email already exists!', 'Close');
          // }
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
    if (!this.childUserInfoFormularComponent.userInformation.location ||
      this.childUserInfoFormularComponent.userInformation.location.locationName === '') {
      addUserErrorMessage += 'Please choose a location for the new user.\n';
    }
    if (!this.childUserInfoFormularComponent.userInformation.startDate) {
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

}
