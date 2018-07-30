import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {ToDoListForBuddyComponent} from './DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {DialogEditProfileComponent} from './DialogEditProfile/dialog-edit-profile.component';
import {DialogNewEmployeeComponent} from './DialogNewEmployee/dialog-new-employee.component';
import {UserAddComponent} from '../users/user-add/user-add.component';
import {DialogDeleteUsersComponent} from './DialogDeleteUsers/dialog-delete-users.component';

@Injectable()
export class CommonComponentsService {

  constructor(private dialog: MatDialog) {
  }


  openModalNewEmployee() {
    this.dialog.open(DialogNewEmployeeComponent, {
      height: '90%',
      width: '900px',
    });
  }

  openModalAddNewUser() {
    this.dialog.open(UserAddComponent, {
      height: '90%',
      width: '670px',
    });
  }

  modalDeleteUser() {
    this.dialog.open(DialogDeleteUsersComponent, {
      height: '90%',
      width: '900px',
    });
  }

  openDialogWithToDOListForBuddy() {
  this.dialog.open(ToDoListForBuddyComponent, {
      height: '90%',
      width: '900px'
    });
  }

  openEditProfileDialog() {
    this.dialog.open(DialogEditProfileComponent, {
      height: '40%',
      width: '600px'
    });
  }


}
