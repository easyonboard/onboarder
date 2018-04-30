import {UserDTO, UserInformationDTO} from '../../domain/user';
import {Component, OnInit} from '@angular/core';
import {MatDialog} from '@angular/material';
import {UserInfoFormularComponent} from '../../users/user-info-formular/user-info-formular.component';
import {UserInformationService} from '../../service/user-information.service';
import {DialogCheckListComponent} from '../DialogCheckList/dialog-check-list.component';
import {UserInfoUpdateComponent} from '../../users/user-info-update/user-info-update.component';
import {CommonComponentsService} from '../common-components.service';

@Component({
  selector: 'app-dialog-new-employee',
  templateUrl: './dialog-new-employee.html'
})
export class DialogNewEmployeeComponent implements OnInit {
  private mailSent: boolean;
  public newEmployees: UserInformationDTO[];
  public allNewEmployees: UserInformationDTO[];
  public searchValue = '';

  constructor(private userInformationService: UserInformationService, private dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.mailSent = false;
    this.newEmployees = [];

    this.userInformationService.getNewUsers().subscribe(newEmployees => {
      this.allNewEmployees = newEmployees;
      this.newEmployees = newEmployees;
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
    this.dialog.open(UserInfoUpdateComponent, {
      height: '650px',
      width: '900px',
      data: userInformation
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
