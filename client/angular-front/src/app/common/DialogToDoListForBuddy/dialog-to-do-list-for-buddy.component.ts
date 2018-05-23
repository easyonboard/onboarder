import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-todocomponntforbuddy',
  templateUrl: './dialog-to-do-list-for-buddy.component.html'
})
export class ToDoListForBuddyComponent implements OnInit {
  toDoList: string[] = [];

  ngOnInit(): void {
    this.toDoList.push('welcome your new colleague on it\'s first day');
    this.toDoList.push('Access key for the new colleague');
    this.toDoList.push('Present the team to the new employee');
    this.toDoList.push('Informing about exceptions about holidays');
    this.toDoList.push('Initiating project accounts for the new employee');
    this.toDoList.push('General presentation of the project');
    this.toDoList.push('Presentation of the project\'s time booking recording modalities');
    this.toDoList.push('Tour of the building on Ferdinand Street');
    this.toDoList.push('Information on the next start-tag date');
  }


}
