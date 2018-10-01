import {TSMap} from 'typescript-map';
import {UserDTO} from '../../domain/user';
import {MAT_DIALOG_DATA, MatSnackBar} from '@angular/material';
import {Component, Inject, OnInit} from '@angular/core';
import {CheckListProperties} from '../../util/CheckListProperties';
import {UserService} from '../../service/user.service';
import {UserInformationService} from '../../service/user-information.service';

@Component({
  selector: 'app-dialog-check-list-user',
  templateUrl: './dialog-check-list.component.html',
})
export class DialogCheckListComponent implements OnInit {
  [x: string]: any;
  public dialogTitle: string;
  public checkList: TSMap<string, boolean>;
  public checkListProperties: CheckListProperties;
  private userInfo: UserDTO;

  constructor(@Inject(MAT_DIALOG_DATA) private user: UserDTO, private userService: UserService, public snackBarCheck: MatSnackBar, private userInformationService: UserInformationService) {
  }

  ngOnInit() {
    this.userInfo = new UserDTO();
    this.dialogTitle = 'Check list for ' + this.user.name;
    this.checkList = new TSMap<string, boolean>();
    this.checkListProperties = new CheckListProperties();
    this.userInformationService.getUserInformation(this.user.username).subscribe(resp => this.userInfo = resp);
    this.userService.getCheckListForUser(this.user).subscribe(
      data => {
        Object.keys(data).forEach(key => {
          this.checkList.set(key, data[key]);
        });
      },
      err => {
        this.snackBarMessagePopup(err.error.message, 'Close');
      });
  }

  get checkListKeys() {
    return Array.from(this.checkList.keys());
  }

  onCheck(key: string) {
    if (this.userInfo.buddyUser === null && key === 'hasBuddyAssigned') {
      this.snackBarMessagePopup('User has no buddy assigned!', 'Close');
      return;
    }
    this.checkList.set(key, !this.checkList.get(key));


  }

  saveStatus() {
    if (this.userInfo.buddyUser == null && this.checkList.get('hasBuddyAssigned')) {
      this.snackBarMessagePopup('User has no buddy assigned!', 'Close');
      return;
    }
    this.userService.saveCheckList(this.user.username, this.checkList).subscribe(value => {
      },
      err => {
        this.snackBarMessagePopup(err.error.message, 'Close');
      });
  }


  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }

}
