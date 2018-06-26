import {UserDTO, UserInformationDTO} from '../../domain/user';
import {Component, OnInit, ViewEncapsulation, ViewChild, AfterViewChecked} from '@angular/core';
import {MatDialog, MatTooltip} from '@angular/material';
import {UserInformationService} from '../../service/user-information.service';
import {DialogCheckListComponent} from '../DialogCheckList/dialog-check-list.component';
import {UserInfoUpdateComponent} from '../../users/user-info-update/user-info-update.component';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-dialog-new-employee',
  templateUrl: './dialog-new-employee.html',
  styleUrls: ['./dialog-new-employee.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DialogNewEmployeeComponent implements OnInit, AfterViewChecked {

  public newEmployees: UserInformationDTO[];

  public isMailSent: Boolean[];
  public allNewEmployees: UserInformationDTO[];
  public searchValue = '';

  public new_employees_msg = 'This is a list with all the future employees.';
  public search_msg = 'Here you can search by name throuh employees.';
  public username_msg = 'This is the name of the employee.';
  public start_date_msg = 'Here is the first day of work for the new employee.';
  public incomplete_msg = 'This indicated that the inforamtion about the new commer is not yet complete. You have to complete it.';
  public checklist_msg = 'Here is a list that tracks the things to be done before the first day of work for the new employee.';

  public popups_visible = true;

  @ViewChild('tooltipNewEmployees') tooltipNewEmployees: MatTooltip;
  @ViewChild('tooltipSearch') tooltipSearch: MatTooltip;
  @ViewChild('tooltipUsername') tooltipUsername: MatTooltip;
  @ViewChild('tooltipStartDate') tooltipStartDate: MatTooltip;
  @ViewChild('tooltipIncomplete') tooltipIncomplete: MatTooltip;
  @ViewChild('tooltipChecklist') tooltipChecklist: MatTooltip;

  ngAfterViewChecked() {
    if (this.tooltipNewEmployees !== undefined && this.tooltipNewEmployees._isTooltipVisible() === false) {
      this.tooltipNewEmployees.showDelay = 400;
      this.tooltipNewEmployees.show();
    }
    if (this.tooltipSearch !== undefined && this.tooltipSearch._isTooltipVisible() === false) {
      this.tooltipSearch.showDelay = 400;
      this.tooltipSearch.show();
    }
    if (this.tooltipUsername !== undefined && this.tooltipUsername._isTooltipVisible() === false) {
      this.tooltipUsername.showDelay = 400;
      this.tooltipUsername.show();
    }
    if (this.tooltipStartDate !== undefined && this.tooltipStartDate._isTooltipVisible() === false) {
      this.tooltipStartDate.showDelay = 400;
      this.tooltipStartDate.show();
    }
    if (this.tooltipIncomplete !== undefined && this.tooltipIncomplete._isTooltipVisible() === false) {
      this.tooltipIncomplete.showDelay = 400;
      this.tooltipIncomplete.show();
    }
    if (this.tooltipChecklist !== undefined && this.tooltipChecklist._isTooltipVisible() === false) {
      this.tooltipChecklist.showDelay = 400;
      this.tooltipChecklist.show();
    }
  }

  constructor(private userInformationService: UserInformationService, private dialog: MatDialog, private userService: UserService) {
  }

  ngOnInit(): void {

    this.newEmployees = [];
    this.isMailSent = [];
    this.userInformationService.getNewUsers().subscribe(newEmployees => {
      this.allNewEmployees = newEmployees;
      this.newEmployees = newEmployees;
      this.getStatus();
      this.setStartDate();
    });
  }

  disablePopups(): void {
    this.popups_visible = false;
    this.tooltipChecklist.disabled = true;
    this.tooltipIncomplete.disabled = true;
    this.tooltipNewEmployees.disabled = true;
    this.tooltipSearch.disabled = true;
    this.tooltipStartDate.disabled = true;
    this.tooltipUsername.disabled = true;
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
      console.log(user.team + '\n');
      console.log(user.userAccount.name + '\n');
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

  private setStartDate() {
    this.newEmployees.forEach(user => {
      const myDate = new Date(user.startDate).toDateString();
      user.startDateString = myDate;
    });
  }
}
