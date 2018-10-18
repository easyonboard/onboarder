import {Component, OnInit} from '@angular/core';
import {MatDialog, MatSnackBar} from '@angular/material';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-dialog-edit-profile',
  templateUrl: './dialog-edit-profile.component.html',
})
export class DialogEditProfileComponent implements OnInit {
  public message: string;
  public username: string;
  public email: string;

  constructor(private userService: UserService,
              public snackBar: MatSnackBar,
              private dialog: MatDialog) {
    this.username = localStorage.getItem('userLogged');
  }

  ngOnInit(): void {
  }


  updateUser(password: string, passwordII: string): void {
    password = password.trim();
    passwordII = passwordII.trim();

    if (password === '' || passwordII === '') {
      this.snackBarMessagePopup('Password can not be null', 'Close');
    } else if (password !== '' && (password !== passwordII || password.length < 6)) {
      this.snackBarMessagePopup('Password not matching or does not have 6 characters', 'Close');
      return;
    } else {
      this.userService.updatePassword(this.username, password).subscribe(
        res => {
          this.dialog.closeAll();
          this.snackBarMessagePopup('Succes!', 'Close');
        },
        err => {
          this.snackBarMessagePopup(err.error.message, 'Close');
        });
    }
  }

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 3000
    });
  }
}
