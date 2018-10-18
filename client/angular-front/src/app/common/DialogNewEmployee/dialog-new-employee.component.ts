import {User} from '../../domain/user';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MatDialog, MatSnackBar} from '@angular/material';
import {DialogCheckListComponent} from '../DialogCheckList/dialog-check-list.component';
import {UserService} from '../../service/user.service';
import {TSMap} from 'typescript-map';
import {UserAddComponent} from '../../users/user-add/user-add-update.component';

@Component({
  selector: 'app-dialog-new-employee',
  templateUrl: './dialog-new-employee.html',
  styleUrls: ['./dialog-new-employee.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DialogNewEmployeeComponent implements OnInit {
  public newEmployees: User[];
  public allNewEmployees: User[];
  public searchValue = '';
  public mailSentValueForUsers: TSMap<number, Boolean>;

  constructor(private dialog: MatDialog,
              private userService: UserService,
              private snackBar: MatSnackBar,) {
  }

  ngOnInit(): void {

    this.newEmployees = [];
    this.allNewEmployees = [];
    this.mailSentValueForUsers = new TSMap<number, Boolean>();
    this.userService.getNewUsers().subscribe(newEmployees => {
        this.allNewEmployees = newEmployees;
        this.newEmployees = newEmployees;
        this.getStatus();
        this.setStartDate();
      },
      err => {
        this.snackBarMessagePopup(err.error.message, 'Close');
      });
  }

  openCheckList(user: User) {
    this.dialog.open(DialogCheckListComponent, {
      height: '650px',
      width: '900px',
      data: user
    });
  }

  openUserInfoModal(user: User) {
    this.dialog.open(UserAddComponent, {
      height: '95%',
      width: '570px',
      data: user
    });
  }

  private getStatus() {
    this.newEmployees.forEach((user, index) => {
      this.userService.isMailSent(user).subscribe(value => {
        this.mailSentValueForUsers.set(user.idUser, value);
      });
    });
  }

  searchByName() {
    if (this.searchValue !== '' && this.searchValue !== null) {
      this.newEmployees = this.allNewEmployees.filter(user => user.name.toLowerCase().includes(this.searchValue.toLowerCase()));
    } else {
      this.newEmployees = this.allNewEmployees;
    }

  }

  private setStartDate() {
    this.newEmployees.forEach(user => {
      const myDate = new Date(user.startDate).toDateString();
      user.startDateString = myDate;
    });
  }

  private snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }
}
