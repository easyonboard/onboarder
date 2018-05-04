import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatSelectChange, MatSnackBar} from '@angular/material';

import {RoleDTO, RoleType} from '../../domain/role';
import {UserDTO, UserInformationDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {UserInformationService} from '../../service/user-information.service';

import {UserInfoFormularComponent} from '../user-info-formular/user-info-formular.component';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent implements OnInit {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  public firstName: string;
  public lastName: string;
  public roleType = RoleType;
  public selectedRole: RoleType;

  public user = new UserDTO();
  public roles = Object.keys(RoleType);

  constructor(private userService: UserService, private userInformationService: UserInformationService,
              public snackBar: MatSnackBar, private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  addUser(): void {
    this.user.username = this.firstName + this.lastName;
    this.user.name = this.firstName + ' ' + this.lastName;

    this.userService.addUser(this.user, this.selectedRole, this.childUserInfoFormularComponent.userInformation).subscribe(
      value => {
        if (this.childUserInfoFormularComponent.userInformation.startDate === undefined) {
          this.snackBarMessagePopup('You must specify the starting date!');
        } else {
          this.snackBarMessagePopup('Succes! You just add a new employee!');
          this.dialog.closeAll();
        }
      },
      error => this.snackBarMessagePopup('Insucces! An error has ocurred!')
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
