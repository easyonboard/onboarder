import {Component, Inject, OnInit} from "@angular/core";
import {MAT_DIALOG_DATA} from "@angular/material";
import {UserService} from "../../service/user.service";
import {LeaveCheckList} from "../../domain/user";


@Component({
  selector: 'app-dialog-leave-check-list-user',
  templateUrl: './dialog-leave-check-list.component.html',
})
export class DialogLeaveCheckListComponent implements OnInit {
  public dialogTitle: string;
  public leaveCheckList: LeaveCheckList;


  constructor(@Inject(MAT_DIALOG_DATA) private user: string, private userService: UserService) {
  }

  ngOnInit() {
    this.leaveCheckList = new LeaveCheckList();
    this.userService.getUserLeaveCheckList(this.user).subscribe(res => this.leaveCheckList = res);
  }


  saveStatus() {
    console.log(this.leaveCheckList);
    this.userService.saveLeaveCheckList(this.leaveCheckList).subscribe();
  }

}
