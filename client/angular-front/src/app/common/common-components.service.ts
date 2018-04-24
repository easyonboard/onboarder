import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {ToDoListForBuddyComponent} from './DialogToDoListForBuddy/dialog-to-do-list-for-buddy.component';
import {DialogEditProfileComponent} from './DialogEditProfile/dialog-edit-profile.component';

@Injectable()
export class CommonComponentsService {

  constructor(private dialog: MatDialog) {
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
