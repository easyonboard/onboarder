import {Injectable} from '@angular/core';
import {MatDialog} from '@angular/material';
import {ToDoListForBuddyComponent} from './ToDoListForBuddy/toDoListForBuddy.component';

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


}
