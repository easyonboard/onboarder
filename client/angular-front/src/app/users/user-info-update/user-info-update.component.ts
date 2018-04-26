import {Component, OnInit, ViewChild, Inject} from '@angular/core';
import {MAT_DIALOG_DATA} from '@angular/material';

import {UserInformationService} from '../../service/user-information.service';
import {UserService} from '../../service/user.service';

import {UserInfoFormularComponent} from '../user-info-formular/user-info-formular.component';
import {UserDTO, UserInformationDTO} from '../../domain/user';

@Component({
  selector: 'app-user-info-update',
  templateUrl: './user-info-update.component.html',
  styleUrls: ['./user-info-update.component.css']
})
export class UserInfoUpdateComponent implements OnInit {

  @ViewChild(UserInfoFormularComponent)
  private childUserInfoFormularComponent: UserInfoFormularComponent;

  constructor(@Inject(MAT_DIALOG_DATA) public userInformation: UserInformationDTO,
              private userInformationService: UserInformationService, private userService: UserService) {
  }

  ngOnInit() {
    this.childUserInfoFormularComponent.userInformation.idUserInformation = this.userInformation.idUserInformation;
    this.childUserInfoFormularComponent.userInformation.team = this.userInformation.team;
    this.childUserInfoFormularComponent.userInformation.building = this.userInformation.building;
    this.childUserInfoFormularComponent.userInformation.floor = this.userInformation.floor;
    this.childUserInfoFormularComponent.userInformation.project = this.userInformation.project;
    this.childUserInfoFormularComponent.userInformation.department = this.userInformation.department;
    this.childUserInfoFormularComponent.userInformation.buddyUser = this.userInformation.buddyUser;
    this.childUserInfoFormularComponent.userInformation.startDate = this.userInformation.startDate;
  }

  updateUserInformation(): void {
    this.userInformationService.updateUserInformation(this.childUserInfoFormularComponent.userInformation).subscribe(
      value => alert('yes!'),
      error => alert('No!')
    );
  }

}
