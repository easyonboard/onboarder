import {Component, OnInit, ViewEncapsulation, ViewChild, AfterViewChecked} from '@angular/core';
import {UserDTO} from '../../domain/user';
import {UserService} from '../../service/user.service';
import {MatDialog, MatSnackBar, MatTooltip} from '@angular/material';
import {DialogLeaveCheckListComponent} from '../DialogLeaveCheckList/dialog-leave-check-list.component';
import {LocalStorageConst} from '../../util/LocalStorageConst';

@Component({
  selector: 'app-dialog-delete-users',
  templateUrl: './dialog-delete-users.html',
  styleUrls: ['./dialog-delete-users.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DialogDeleteUsersComponent implements OnInit, AfterViewChecked {
  public searchValue = '';
  dialogDeleteUsers: string;
  public filteredUsers: UserDTO[];
  public allUsers: UserDTO[];
  public canUserBeDeleted: Boolean;

  public delete_employee_msg = 'This is a list with all the employees. From here, you can delete them from the system';
  public search_msg = 'Here you can search by name throuh employees.';
  public username_msg = 'This is the name of the employee.';
  public user_email_msg = 'This is the personal email address of the employee.';
  public leave_checklist_msg = 'Here is a list that tracks the things that MUST be completed before the employee leaves.';
  public remove_msg = 'Press the \'Remove user\' button to remove the employee from the system. Note that all the items from the list above MUST be checked in order to delete a user.';

  public popups_visible = true;

  @ViewChild('tooltipDeleteEmployee') tooltipDeleteEmployee: MatTooltip;
  @ViewChild('tooltipSearch') tooltipSearch: MatTooltip;
  @ViewChild('tooltipUsername') tooltipUsername: MatTooltip;
  @ViewChild('tooltipUserEmail') tooltipUserEmail: MatTooltip;
  @ViewChild('tooltipLeaveChecklist') tooltipLeaveChecklist: MatTooltip;
  @ViewChild('tooltipRemove') tooltipRemove: MatTooltip;

  ngAfterViewChecked() {
    if (this.tooltipDeleteEmployee !== undefined && this.tooltipDeleteEmployee._isTooltipVisible() === false) {
      this.tooltipDeleteEmployee.showDelay = 400;
      this.tooltipDeleteEmployee.show();
    }
    if (this.tooltipSearch !== undefined && this.tooltipSearch._isTooltipVisible() === false) {
      this.tooltipSearch.showDelay = 400;
      this.tooltipSearch.show();
    }
    if (this.tooltipUsername !== undefined && this.tooltipUsername._isTooltipVisible() === false) {
      this.tooltipUsername.showDelay = 400;
      this.tooltipUsername.show();
    }
    if (this.tooltipUserEmail !== undefined && this.tooltipUserEmail._isTooltipVisible() === false) {
      this.tooltipUserEmail.showDelay = 400;
      this.tooltipUserEmail.show();
    }
    if (this.tooltipLeaveChecklist !== undefined && this.tooltipLeaveChecklist._isTooltipVisible() === false) {
      this.tooltipLeaveChecklist.showDelay = 400;
      this.tooltipLeaveChecklist.show();
    }
    if (this.tooltipRemove !== undefined && this.tooltipRemove._isTooltipVisible() === false) {
      this.tooltipRemove.showDelay = 400;
      this.tooltipRemove.show();
    }
  }

  constructor(private userService: UserService, private dialog: MatDialog, public snackBarDelete: MatSnackBar) {
  }

  ngOnInit(): void {
    this.dialogDeleteUsers = 'My title!';
    this.getAllUsers();
  }

  disablePopups(): void {
    this.popups_visible = false;
    this.tooltipLeaveChecklist.disabled = true;
    this.tooltipDeleteEmployee.disabled = true;
    this.tooltipSearch.disabled = true;
    this.tooltipUsername.disabled = true;
    this.tooltipRemove.disabled = true;
    this.tooltipUserEmail.disabled = true;
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
