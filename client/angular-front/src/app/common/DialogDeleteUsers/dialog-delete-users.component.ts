import {Component, OnInit, ViewEncapsulation, ViewChild} from '@angular/core';
import {UserDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {MatDialog, MatSnackBar} from '@angular/material';
import {DialogLeaveCheckListComponent} from '../DialogLeaveCheckList/dialog-leave-check-list.component';
import {LocalStorageConst} from '../../util/LocalStorageConst';

@Component({
  selector: 'app-dialog-delete-users',
  templateUrl: './dialog-delete-users.html',
  styleUrls: ['./dialog-delete-users.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DialogDeleteUsersComponent implements OnInit {
  public searchValue = '';
  dialogDeleteUsers: string;
  public filteredUsers: UserDTO[];
  public allUsers: UserDTO[];
  public canUserBeDeleted: Boolean;

  constructor(private userService: UserService, private dialog: MatDialog, public snackBarDelete: MatSnackBar) {
  }

  ngOnInit(): void {
    this.dialogDeleteUsers = 'My title!';
    this.getAllUsers();
  }

  remove(username: String) {
    this.userService.removeUser(username).subscribe(res => {
      if (res === false) {
        this.snackBarMessagePopup('User can not be removed! Please verify Leave Check List');
      } else {
        this.snackBarMessagePopup('User ' + username + ' removed!');
        this.getAllUsers();
      }
    });
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe(users => {
      this.allUsers = users
      this.allUsers.forEach(user => {
        if (user.username === localStorage.getItem(LocalStorageConst._USER_LOGGED)) {
          this.allUsers.splice(this.allUsers.indexOf(user),1 );
        }
      });
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

  openLeaveCheckList(user: string) {
    this.dialog.open(DialogLeaveCheckListComponent, {
      height: '350px',
      width: '700px',
      data: user
    });
  }

  snackBarMessagePopup(message: string) {
    this.snackBarDelete.open(message, null, {
      duration: 3000
    });
  }
}
