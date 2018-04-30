import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {ToDoListForBuddyComponent} from './DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {DialogEditProfileComponent} from './DialogEditProfile/dialog-edit-profile.component';
import {DialogEnrolledCoursesForUserComponent} from './DialogEnrolledCoursesForUser/dialog-enrolled-courses-for-user.component';
import {DialogNewEmployeeComponent} from './DialogNewEmployee/dialog-new-employee.component';
import {UserAddComponent} from '../users/user-add/user-add.component';
import {DialogDeleteUsersComponent} from './DialogDeleteUsers/dialog-delete-users.component';

@Injectable()
export class CommonComponentsService {

  constructor(private dialog: MatDialog) {
  }

  openDialogCoursesForUser() {
    this.dialog.open(DialogEnrolledCoursesForUserComponent, {
      height: '650px',
      width: '900px'
    });
  }

  openModalNewEmployee() {
    this.dialog.open(DialogNewEmployeeComponent, {
      height: '700px',
      width: '900px',
    });
  }

  openModalAddNewUser() {
    this.dialog.open(UserAddComponent, {
      height: '850px',
      width: '670px',
    });
  }

  modalDeleteUser() {
    this.dialog.open(DialogDeleteUsersComponent, {
      height: '650px',
      width: '900px',
    });
  }

  openDialogWithToDOListForBuddy() {
    const dialogRef = this.dialog.open(ToDoListForBuddyComponent, {
      height: '650px',
      width: '900px'
    });
  }

  openEditProfileDialog() {
    this.dialog.open(DialogEditProfileComponent, {
      height: '400px',
      width: '600px'
    });
  }


}
