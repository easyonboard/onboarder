import {Component, OnInit} from "@angular/core";

@Component({
  selector: 'app-dialog-delete-users',
  templateUrl: './dialog-delete-users.html',
})
export class DialogDeleteUsersComponent implements OnInit {
  dialogDeleteUsers: string;

  ngOnInit(): void {
    this.dialogDeleteUsers = 'My title!';
  }

}
