import {Component, OnInit, ViewChild, ViewEncapsulation, AfterViewChecked} from '@angular/core';
import {MatDialog, MatSelectChange, MatSnackBar, MatTooltip} from '@angular/material';

import {RoleDTO, RoleType} from '../../domain/role';
import {UserDTO, UserInformationDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {UserInformationService} from '../../service/user-information.service';

import {UserInfoFormularComponent} from '../user-info-formular/user-info-formular.component';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserAddComponent implements OnInit, AfterViewChecked {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  public firstName: string;
  public lastName: string;
  public roleType = RoleType;
  public selectedRole: RoleType;

  public user = new UserDTO();
  public roles = Object.keys(RoleType);

  public popups_visible = true;

  public add_user_msg = 'Complete the formular with the required data and press \'ADD USER\'';
  public username_msg = 'The username is created automatically using the first and last names';
  public must_msg = 'fields marked with \'*\' must be completed now; the rest of them can be completed later.';

  @ViewChild('tooltipAdd') tooltipAdd: MatTooltip;
  @ViewChild('tooltipMust') tooltipMust: MatTooltip;
  @ViewChild('tooltipUsername') tooltipUsername: MatTooltip;

  ngAfterViewChecked() {
    if (this.tooltipAdd !== undefined && this.tooltipAdd._isTooltipVisible() === false) {
      this.tooltipAdd.show();
    }
    if (this.tooltipMust !== undefined && this.tooltipMust._isTooltipVisible() === false) {
      this.tooltipMust.show();
    }
    if (this.tooltipUsername !== undefined && this.tooltipUsername._isTooltipVisible() === false) {
      this.tooltipUsername.show();
    }
  }

  constructor(private userService: UserService, private userInformationService: UserInformationService,
              public snackBar: MatSnackBar, private dialog: MatDialog) {
  }

  ngOnInit(): void {
  }

  disablePopups(): void {
    this.popups_visible = false;
    this.tooltipUsername.disabled = true;
    this.tooltipAdd.disabled = true;
    this.tooltipMust.disabled = true;
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
