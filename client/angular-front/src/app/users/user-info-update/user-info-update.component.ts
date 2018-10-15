import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatSnackBar} from '@angular/material';

import {UserInformationService} from '../../service/user-information.service';

import {UserInfoFormularComponent} from '../user-info-formular/user-info-formular.component';
import {User} from '../../domain/user';

@Component({
  selector: 'app-user-info-update',
  templateUrl: './user-info-update.component.html',
  styleUrls: ['./user-info-update.component.css']
})
export class UserInfoUpdateComponent implements OnInit {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  constructor(@Inject(MAT_DIALOG_DATA) public userInformation: User,
              private userInformationService: UserInformationService, private snackBar: MatSnackBar) {
  }

  ngOnInit() {
    console.log(this.userInformation);
    this.childUserInfoFormularComponent.userInformation.idUser = this.userInformation.idUser;
    this.childUserInfoFormularComponent.userInformation.team = this.userInformation.team;
    this.childUserInfoFormularComponent.userInformation.location = this.userInformation.location;
    this.childUserInfoFormularComponent.userInformation.floor = this.userInformation.floor;
    this.childUserInfoFormularComponent.userInformation.project = this.userInformation.project;
    this.childUserInfoFormularComponent.userInformation.department = this.userInformation.department;
    this.childUserInfoFormularComponent.userInformation.mate = this.userInformation.mate;
    this.childUserInfoFormularComponent.userInformation.startDate = this.userInformation.startDate;
  }

  updateUserInformation(): void {
    this.userInformationService.updateUserInformation(this.childUserInfoFormularComponent.userInformation).subscribe(
      value => {
        this.snackBarMessagePopup('User info updated!', 'Close');
        this.userInformationService.getUserInformation(this.userInformation.username).subscribe(resp => {
          this.userInformation = resp;
          this.childUserInfoFormularComponent.userInformation.idUser = this.userInformation.idUser;
          this.childUserInfoFormularComponent.userInformation.team = this.userInformation.team;
          this.childUserInfoFormularComponent.userInformation.location = this.userInformation.location;
          this.childUserInfoFormularComponent.userInformation.floor = this.userInformation.floor;
          this.childUserInfoFormularComponent.userInformation.project = this.userInformation.project;
          this.childUserInfoFormularComponent.userInformation.department = this.userInformation.department;
          this.childUserInfoFormularComponent.userInformation.mate = this.userInformation.mate;
          this.childUserInfoFormularComponent.userInformation.startDate = this.userInformation.startDate;
        });

      },
      error => this.snackBarMessagePopup(error.error.message, 'Close')
    );
  }

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }

}
