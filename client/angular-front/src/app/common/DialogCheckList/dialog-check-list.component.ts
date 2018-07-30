import {TSMap} from 'typescript-map';
import {UserDTO} from '../../domain/user';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {Component, Inject, OnInit} from '@angular/core';
import {CheckListProperties} from '../../util/CheckListProperties';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-dialog-check-list-user',
  templateUrl: './dialog-check-list.component.html',
})
export class DialogCheckListComponent implements OnInit {
  [x: string]: any;
  public dialogTitle: string;
  public checkList: TSMap<string, boolean>;
  public checkListProperties: CheckListProperties;

  constructor(@Inject(MAT_DIALOG_DATA) private user: UserDTO, private userService: UserService) {
  }

  ngOnInit() {
    this.dialogTitle = 'Check list for ' + this.user.name;
    this.checkList = new TSMap<string, boolean>();
    this.checkListProperties = new CheckListProperties();
    this.userService.getCheckListForUser(this.user).subscribe(
      data => {
        Object.keys(data).forEach(key => {
          this.checkList.set(key, data[key]);
        });
      },
        err => {
          this.snackBarMessagePopup(err.error.message);
        });
  }

  get checkListKeys() {
    return Array.from(this.checkList.keys());
  }

  onCheck(key: string) {

    this.checkList.set(key, !this.checkList.get(key));

  }

  saveStatus() {

    this.userService.saveCheckList(this.user.username, this.checkList).subscribe(value => {},
      err => {
      this.snackBarMessagePopup(err.error.message);
    });
  }

}
