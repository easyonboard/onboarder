import {UserDTO, UserInformationDTO} from '../../domain/user';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserInformationService} from '../../service/user-information.service';
import {DialogCheckListComponent} from '../DialogCheckList/dialog-check-list.component';
import {UserInfoUpdateComponent} from '../../users/user-info-update/user-info-update.component';
import {UserService} from "../../service/user.service";
import {TSMap} from "typescript-map";

@Component({
  selector: 'app-dialog-new-employee',
  templateUrl: './dialog-new-employee.html'
})
export class DialogNewEmployeeComponent implements OnInit {

  public newEmployees: UserInformationDTO[];
  checkList: TSMap<string, boolean>;
  public isMailSent: Boolean[];

  constructor(private userInformationService: UserInformationService, private dialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {

    this.newEmployees = [];
    this.isMailSent = [];
    this.getUsers();


  }

  openCheckList(user: UserDTO) {

    this.dialog.open(DialogCheckListComponent, {
      height: '650px',
      width: '900px',
      data: user
    });
  }

  openUserInfoModal(userInformation: UserInformationDTO) {
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

  private getUsers() {

    this.userInformationService.getNewUsers().subscribe(newEmployees => {
      this.newEmployees = newEmployees;
      this.getStatus();
    });
  }
}
