import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {Observable} from 'rxjs/Observable';
import {EventDTO, MeetingHall} from '../domain/event';
import {UserDTO} from '../domain/user';
import {LocationDTO} from '../domain/location';

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

  addEvent(event: EventDTO, contactPerson: string, enrolledPersons: string[], selectedLocation: LocationDTO, hall: MeetingHall): any {
    let body = JSON.stringify({event: event, contactPersons: contactPerson, enrolledPersons: enrolledPersons, location: selectedLocation, hall: hall});
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
