import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {Observable} from 'rxjs/Observable';
import {EventDTO} from '../domain/event';
import {UserDTO} from '../domain/user';

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

  addEvent(event: EventDTO, contactPersons: any[], enrolledPersons: any[], selectedLocation: any[]): any {
    let body = JSON.stringify({event: event, contactPersons: contactPersons, enrolledPersons: enrolledPersons, location: selectedLocation});
    return this.http.post<EventDTO>(this.rootConst.SERVER_ADD_EVENT, body, this.httpOptions);
  }

  getPastEvents(): Observable<EventDTO[]> {
    return this.http.get<EventDTO[]>(`${this.rootConst.SERVER_PAST_EVENT}`);
  }

  getUpcomingEvents(): Observable<EventDTO[]> {
    return this.http.get<EventDTO[]>(`${this.rootConst.SERVER_UPCOMING_EVENT}`);
  }

  enrollUser(user: UserDTO, event: EventDTO):Observable<EventDTO[]> {
    const body = JSON.stringify({eventID: event.idEvent, user: user});
    return this.http.post<EventDTO[]>(this.rootConst.SERVER_ENROLL_USER, body, this.httpOptions);
  }
}
