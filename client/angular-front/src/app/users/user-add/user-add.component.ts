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
    this.userService.checkUnicity(this.user.username, this.user.msgMail).subscribe(
      value => {
        unique = value;

        if (unique === true) {
          this.userService.addUser(this.user, this.selectedRole, this.childUserInfoFormularComponent.userInformation).subscribe(
            value2 => {
              if (this.childUserInfoFormularComponent.userInformation.startDate === undefined) {
                this.snackBarMessagePopup('You must specify the starting date!');
              } else {
                this.snackBarMessagePopup('Succes! You just add a new employee!');
                this.dialog.closeAll();
              }
            },
            error => this.snackBarMessagePopup('Failed! An error has ocurred!')
          );
        } else {
          this.snackBarMessagePopup('Failed! Duplicated username or .msg email!');
        }
      },
      error => this.snackBarMessagePopup('Failed! An error has ocurred!')
    );
  }

  selectRoleValue(event: MatSelectChange) {
    this.selectedRole = event.value;
  }

  snackBarMessagePopup(message: string) {
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }

}
