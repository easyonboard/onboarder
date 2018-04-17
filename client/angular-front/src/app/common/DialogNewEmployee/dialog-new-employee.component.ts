import {UserDTO, UserInformationDTO} from '../../domain/user';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserInfoFormularComponent} from '../../users/user-info-formular/user-info-formular.component';
import {UserInformationService} from '../../service/user-information.service';
import {DialogCheckListComponent} from '../DialogCheckList/dialog-check-list.component';

@Component({
  selector: 'app-dialog-new-employee',
  templateUrl: './dialog-new-employee.html'
})
export class DialogNewEmployeeComponent implements OnInit {
  private mailSent: boolean;
  public newEmployees: UserInformationDTO[];

  constructor(private userInformationService: UserInformationService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.mailSent = false;
    this.newEmployees = [];

    this.userInformationService.getNewUsers().subscribe(newEmployees => {
      this.newEmployees = newEmployees;
      console.log(this.newEmployees);
    });
  }

  openCheckList(user: UserDTO) {
    console.log(user);
    this.dialog.open(DialogCheckListComponent, {
      height: '650px',
      width: '900px',
      data: user
    });
  }

  openUserInfoModal(userInformation: UserInformationDTO) {
    this.dialog.open(UserInfoFormularComponent, {
      height: '650px',
      width: '900px',
      data: userInformation

    });
  }

}
