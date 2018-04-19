import {Component, OnInit} from "@angular/core";
import {UserDTO} from "../../domain/user";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-dialog-delete-users',
  templateUrl: './dialog-delete-users.html',
})
export class DialogDeleteUsersComponent implements OnInit {
  dialogDeleteUsers: string;
  private allUsers: UserDTO[];

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.dialogDeleteUsers = 'My title!';
    this.getAllUsers();
  }

  remove(username: String) {
    this.userService.removeUser(username).subscribe(res => {
      this.getAllUsers();
    });

  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe(users => this.allUsers = users);

  }
}
