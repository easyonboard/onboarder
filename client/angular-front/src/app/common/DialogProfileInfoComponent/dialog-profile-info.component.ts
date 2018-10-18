import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserService} from '../../service/user.service';
import {User} from '../../domain/user';

@Component({
  selector: 'app-dialog-profile-info',
  templateUrl: './dialog-profile-info.component.html',
})
export class DialogProfileInfoComponent implements OnInit {
  [x: string]: any;

  public user: User;
  username: string;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.username = localStorage.getItem('username');
    console.log(this.username);
    this.userService.getUserByUsername(this.username).subscribe(
      resp => {
        this.user = resp;
      },
      err => this.snackBarMessagePopup(err.error.message));
    this.userService.getUserByUsername(this.username).subscribe(
      resp => {
        this.userInformation = resp;

      },
      err => this.snackBarMessagePopup(err.error.message));
  }

}
