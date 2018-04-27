import {Component, OnInit} from '@angular/core';
import {UserDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';

@Component({
  selector: 'app-dialog-delete-users',
  templateUrl: './dialog-delete-users.html',
})
export class DialogDeleteUsersComponent implements OnInit {
  public searchValue = '';
  dialogDeleteUsers: string;
  public filteredUsers: UserDTO[];
  public allUsers: UserDTO[];

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
    this.userService.getAllUsers().subscribe(users => {
      this.allUsers = users;
      this.searchByName();
    });
  }

  searchByName() {
    if (this.searchValue !== '' && this.searchValue !== null) {
      this.filteredUsers = this.allUsers.filter(user => user.name.toLowerCase().includes(this.searchValue.toLowerCase()));
    } else {
      this.filteredUsers = this.allUsers;
    }
  }

}
