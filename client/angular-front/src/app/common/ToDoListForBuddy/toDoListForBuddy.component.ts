import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-todocomponntforbuddy',
  templateUrl: './toDoListForBuddy.component.html'
})
export class ToDoListForBuddyComponent implements OnInit {
  toDoList: string[] = [];

  ngOnInit(): void {
    this.toDoList.push('Intampinarea noului coleg');
    this.toDoList.push('Prezentarea colegilor din echipa');
    this.toDoList.push('Informarea despre exceptiile legate de concedii');
    this.toDoList.push('Initializarea conturilor pe proiect pentru noul angajat');
    this.toDoList.push('Prezentarea generala a proiectului');
    this.toDoList.push('Prezentarea modalitatilor de pontare pe proiect');
    this.toDoList.push('Tur al cladirii de pe str. Ferdinand');
    this.toDoList.push('Informare cu privire la data urmatorului start-tag');
  }


}
