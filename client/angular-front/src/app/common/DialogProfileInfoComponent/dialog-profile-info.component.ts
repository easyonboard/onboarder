import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserService} from '../../service/user.service';
import {UserInformationService} from '../../service/user-information.service';
import { UserDTO, UserInformationDTO } from '../../domain/user';

@Component({
  selector: 'app-dialog-profile-info',
  templateUrl: './dialog-profile-info.component.html',
})
export class DialogProfileInfoComponent implements OnInit {
  [x: string]: any;

  public user: UserDTO;
  public userInformation: UserInformationDTO;
  username: string;

  // public userDetails: UserDetails;
  // public userInformationsDetails: UserInformationsDetails;

  constructor(private userService: UserService, private userInfoService: UserInformationService, private dialog: MatDialog) {
    this.username = localStorage.getItem('userLogged');
  }

  ngOnInit() {
    this.userService.getUserByUsername(this.username).subscribe(
      resp => {
        this.user = resp;
        // this.getUserDetails();
      },
      err => this.snackBarMessagePopup(err.error.message));
    this.userInfoService.getUserInformation(this.username).subscribe(
      resp => {
        this.userInformation = resp;
        // this.getUserInformationDetails();
      },
      err => this.snackBarMessagePopup(err.error.message));
  }

  // getUserDetails() {
  //     this.userDetails = new UserDetails();
  //     this.userDetails.username = this.user.username;
  //     this.userDetails.name = this.user.name;
  //     this.userDetails.email = this.user.email;
  //     this.userDetails.msgMail = this.user.msgMail;
  // }

  // getUserInformationDetails() {
  //   this.userInformationsDetails = new UserInformationsDetails();
  //   this.userInformationsDetails.buddyUser = this.userInformation.buddyUser;
  //   this.userInformationsDetails.department = this.userInformation.department;
  //   this.userInformationsDetails.floor = this.userInformation.floor;
  //   this.userInformationsDetails.location = this.userInformation.location;
  //   this.userInformationsDetails.project = this.userInformation.project;
  //   this.userInformationsDetails.startDateString = this.userInformation.startDate;
  //   this.userInformationsDetails.team = this.userInformation.team;
  // }
}
