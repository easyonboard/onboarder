import {Component, OnInit} from '@angular/core';
import {MatDialog, MatSnackBar} from '@angular/material';
import {UserService} from '../../service/user.service';
import {UserInformationService} from "../../service/user-information.service";

@Component({
  selector: 'app-dialog-edit-profile',
  templateUrl: './dialog-edit-profile.component.html',
})
export class DialogEditProfileComponent implements OnInit {
  public message: string;
  public username: string;
  public email: string;
  
  constructor(private userService: UserService, private userInfoService: UserInformationService, public snackBar: MatSnackBar, private dialog: MatDialog) {
    this.username = localStorage.getItem('userLogged');
  }

  ngOnInit(): void {
  }


  updateUser(password: string, passwordII: string): void {
    password = password.trim();
    passwordII = passwordII.trim();

    if (password === '' || passwordII === '') {
      this.snackBarMessagePopup('Password can not be null');
    } else if (password !== '' && (password !== passwordII || password.length < 6)) {
      this.snackBarMessagePopup('Password not matching or does not have 6 characters');
      return;
    } else {
      this.userService.updatePassword(this.username, password).subscribe(
        res => {
          this.dialog.closeAll();
          this.snackBarMessagePopup('Succes!');
        },
        err => {
          this.snackBarMessagePopup(err.error.message);
        });
    }
  }

  snackBarMessagePopup(message: string) {
    this.snackBar.open(message, null, {
      duration: 3000
    });
  }
}
