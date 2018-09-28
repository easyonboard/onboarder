import {UserDTO} from '../../domain/user';
import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserInformationService} from '../../service/user-information.service';
import {DialogCheckListComponent} from '../DialogCheckList/dialog-check-list.component';
import {UserInfoUpdateComponent} from '../../users/user-info-update/user-info-update.component';
import {UserService} from '../../service/user.service';
import {TSMap} from 'typescript-map';

@Component({
  selector: 'app-dialog-new-employee',
  templateUrl: './dialog-new-employee.html',
  styleUrls: ['./dialog-new-employee.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DialogNewEmployeeComponent implements OnInit {
  [x: string]: any;

  public newEmployees: UserDTO[];
  public allNewEmployees: UserDTO[];
  public searchValue = '';
  public mailSentValueForUsers: TSMap<number, Boolean>;

  constructor(private userInformationService: UserInformationService, private dialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {

    this.newEmployees = [];
    this.allNewEmployees = [];
    this.mailSentValueForUsers = new TSMap<number, Boolean>();
    this.userInformationService.getNewUsers().subscribe(newEmployees => {
        this.allNewEmployees = newEmployees;
        this.newEmployees = newEmployees;
        this.getStatus();
debugger
        this.setStartDate();
      },
      err => {
        this.snackBarMessagePopup(err.error.message);
      });
  }

  openCheckList(user: UserDTO) {

    this.dialog.open(DialogCheckListComponent, {
      height: '650px',
      width: '900px',
      data: user
    });
  }

  openUserInfoModal(userInformation: UserDTO) {
    this.dialog.open(UserInfoUpdateComponent, {
      height: '650px',
      width: '900px',
      data: userInformation
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
}
