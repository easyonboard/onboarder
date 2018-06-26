import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {Course} from '../domain/course';
import {Observer} from 'rxjs/Observer';
import {Observable} from 'rxjs/Observable';
import {TutorialMaterialDTO} from '../domain/tutorialMaterial';
import {EventDTO} from '../domain/event';

@Injectable()
export class EventService implements OnInit {

  private rootConst: RootConst = new RootConst();

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  addEvent(event: EventDTO, contactPersons: any[], enrolledPersons: any[]): any {
    let body = JSON.stringify({event: event, contactPersons: contactPersons, enrolledPersons: enrolledPersons});
    return this.http.post<EventDTO>(this.rootConst.SERVER_ADD_EVENT, body, this.httpOptions);
  }

}
