import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {ToDoListForBuddyComponent} from './DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {DialogNewEmployeeComponent} from './DialogNewEmployee/dialog-new-employee.component';
import {UserAddComponent} from '../users/user-add/user-add-update.component';
import {DialogDeleteUsersComponent} from './DialogDeleteUsers/dialog-delete-users.component';
import { DialogProfileInfoComponent } from './DialogProfileInfoComponent/dialog-profile-info.component';

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
      height: '95%',
      width: '570px',
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


  openProfileInfoDialog() {
    this.dialog.open(DialogProfileInfoComponent, {
      height: '50%',
      width: '600px'
    });
  }
}
