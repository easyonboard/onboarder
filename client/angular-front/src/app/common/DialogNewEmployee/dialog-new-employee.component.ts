import {UserDTO, UserInformationDTO} from '../../domain/user';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserInformationService} from '../../service/user-information.service';
import {DialogCheckListComponent} from '../DialogCheckList/dialog-check-list.component';
import {UserInfoUpdateComponent} from '../../users/user-info-update/user-info-update.component';
import {UserService} from "../../service/user.service";


@Component({
  selector: 'app-dialog-new-employee',
  templateUrl: './dialog-new-employee.html'
})
export class DialogNewEmployeeComponent implements OnInit {

  public newEmployees: UserInformationDTO[];

  public isMailSent: Boolean[];

  public allNewEmployees: UserInformationDTO[];
  public searchValue = '';


  constructor(private userInformationService: UserInformationService, private dialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {

    this.newEmployees = [];
    this.isMailSent = [];
    this.userInformationService.getNewUsers().subscribe(newEmployees => {
      this.allNewEmployees = newEmployees;
      this.newEmployees = newEmployees;
      this.getStatus();
    });
  }

  openCheckList(user: UserDTO) {

    this.dialog.open(DialogCheckListComponent, {
      height: '650px',
      width: '900px',
      data: user
    });
  }

  openUserInfoModal(userInformation: UserInformationDTO) {
    console.log('user inainte de modal');
    console.log(userInformation);
    this.dialog.open(UserInfoUpdateComponent, {
      height: '650px',
      width: '900px',
      data: userInformation
    });
  }

  private getStatus() {
    this.newEmployees.forEach(user => {
      this.userService.isMailSent(user.userAccount).subscribe(value => {
        this.isMailSent.push(value);
        console.log(value);
      });
    });
  }

  searchByName() {
    if (this.searchValue !== '' && this.searchValue !== null) {
      this.newEmployees = this.allNewEmployees.filter(user => user.userAccount.name.toLowerCase().includes(this.searchValue.toLowerCase()));
    } else {
      this.newEmployees = this.allNewEmployees;
    }
  }

}
