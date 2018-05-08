import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatSnackBar} from '@angular/material';

import {UserInformationService} from '../../service/user-information.service';

import {UserInfoFormularComponent} from '../user-info-formular/user-info-formular.component';
import {UserInformationDTO} from '../../domain/user';

@Component({
  selector: 'app-user-info-update',
  templateUrl: './user-info-update.component.html',
  styleUrls: ['./user-info-update.component.css']
})
export class UserInfoUpdateComponent implements OnInit {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  constructor(@Inject(MAT_DIALOG_DATA) public userInformation: UserInformationDTO,
              private userInformationService: UserInformationService,  private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    console.log(this.userInformation);
    this.childUserInfoFormularComponent.userInformation.idUserInformation = this.userInformation.idUserInformation;
    this.childUserInfoFormularComponent.userInformation.team = this.userInformation.team;
    this.childUserInfoFormularComponent.userInformation.location = this.userInformation.location;
    this.childUserInfoFormularComponent.userInformation.floor = this.userInformation.floor;
    this.childUserInfoFormularComponent.userInformation.project = this.userInformation.project;
    this.childUserInfoFormularComponent.userInformation.department = this.userInformation.department;
    this.childUserInfoFormularComponent.userInformation.buddyUser = this.userInformation.buddyUser;
    this.childUserInfoFormularComponent.userInformation.startDate = this.userInformation.startDate;
    this.childUserInfoFormularComponent.userInformation.userAccount = this.userInformation.userAccount;

  }

  updateUserInformation(): void {
    this.userInformationService.updateUserInformation(this.childUserInfoFormularComponent.userInformation).subscribe(
      value => this.snackBarMessagePopup('User info updated!'),
      error => this.snackBarMessagePopup('Could not update user info! Please try again!')
    );
  }

  snackBarMessagePopup(message: string) {
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }

}
