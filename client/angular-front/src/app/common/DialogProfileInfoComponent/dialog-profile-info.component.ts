import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserService} from '../../service/user.service';
import {User} from '../../domain/user';
import {LocalStorageConst} from '../../util/LocalStorageConst';

@Component({
  selector: 'app-dialog-profile-info',
  templateUrl: './dialog-profile-info.component.html',
})
export class DialogProfileInfoComponent implements OnInit {
  [x: string]: any;

  public user: User;
  msgMail: string;

  constructor(private userService: UserService) {
  }

  ngOnInit() {
    this.msgMail = localStorage.getItem(LocalStorageConst._MSG_MAIL);
    this.userService.getUserByMsgMail(this.msgMail).subscribe(
      resp => {
        this.user = resp;
      },
      err => this.snackBarMessagePopup(err.error.message));
    this.userService.getUserByMsgMail(this.msgMail).subscribe(
      resp => {
        this.userInformation = resp;

      },
      err => this.snackBarMessagePopup(err.error.message));
  }

}
