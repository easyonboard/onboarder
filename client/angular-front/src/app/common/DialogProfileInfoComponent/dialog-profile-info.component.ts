import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserService} from '../../service/user.service';
import {UserInformationService} from '../../service/user-information.service';
import {UserDTO} from '../../domain/user';

@Component({
  selector: 'app-dialog-profile-info',
  templateUrl: './dialog-profile-info.component.html',
})
export class DialogProfileInfoComponent implements OnInit {
  [x: string]: any;

  public user: UserDTO;
  username: string;

  constructor(private userService: UserService, private userInfoService: UserInformationService, private dialog: MatDialog) {
    this.username = localStorage.getItem('userLogged');
  }

  ngOnInit() {
    this.userService.getUserByUsername(this.username).subscribe(
      resp => {
        this.user = resp;
      },
      err => this.snackBarMessagePopup(err.error.message));
    this.userInfoService.getUserInformation(this.username).subscribe(
      resp => {
        this.userInformation = resp;

      },
      err => this.snackBarMessagePopup(err.error.message));
  }

}
