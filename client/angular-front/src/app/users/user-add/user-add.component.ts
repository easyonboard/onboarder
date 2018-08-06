import {Component, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MatDialog, MatSelectChange, MatSnackBar} from '@angular/material';

import {RoleType} from '../../domain/role';
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
    if (this.user.username !== '' && this.user.msgMail !== '') {
      if (this.childUserInfoFormularComponent.userInformation.location.locationName === '') {
        this.snackBarMessagePopup('Please choose a location', 'Close');
      } else {
        this.userService.checkUnicity(this.user.username, this.user.msgMail).subscribe(
          value1 => {
            unique = value1;

            if (unique === true) {
              if(this.childUserInfoFormularComponent.userInformation.department===undefined){
                this.snackBarMessagePopup('You must specify a department', 'Close');
                return;
              }
              debugger
              if (this.childUserInfoFormularComponent.userInformation.startDate !== undefined) {
                this.userService.addUser(this.user, this.selectedRole, this.childUserInfoFormularComponent.userInformation).subscribe(
                  value2 => {
                    if (this.childUserInfoFormularComponent.userInformation.startDate === undefined) {
                      this.snackBarMessagePopup('You must specify the starting date!', 'Close');
                    } else {
                      this.snackBarMessagePopup('Succes! You just add a new employee!', 'Close');
                      this.dialog.closeAll();
                    }
                  },
                  error => {
                    this.snackBarMessagePopup(error.error.message, 'Close');
                    console.log('error1 ' + error.error.message);
                  }
                );
              } else {
                this.snackBarMessagePopup('You must provide the starting date!', 'Close');
              }
            } else {
              this.snackBarMessagePopup('Failed! Username or .msg email already exists!', 'Close');
            }
          },
          error => {
            this.snackBarMessagePopup(error.error.message, 'Close');
            console.log('error2 ' + error.error.message);
          }
        );
      }
    } else {
      this.snackBarMessagePopup('You must provide the name and .msg email!', 'Close');
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
