import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {Observable} from 'rxjs/Observable';
import {Event, MeetingHall} from '../domain/event';
import {User} from '../domain/user';
import {Location} from '../domain/location';

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

  addEvent(event: Event, contactPerson: string, enrolledPersons: string[], selectedLocation: Location, hall: MeetingHall): any {
    // tslint:disable-next-line:max-line-length
    const body = JSON.stringify({event: event, contactPersons: contactPerson, enrolledPersons: enrolledPersons, location: selectedLocation, hall: hall});
    return this.http.post<Event>(this.rootConst.SERVER_ADD_EVENT, body, this.httpOptions);
  }

  getPastEvents(keyword?: string): Observable<Event[]> {
    if (keyword) {
      return this.http.get<Event[]>(`${this.rootConst.SERVER_PAST_EVENT_FILTER_BY_KEYWORD}${keyword}`);
    }
    return this.http.get<Event[]>(`${this.rootConst.SERVER_PAST_EVENT}`);
  }

  getUpcomingEvents(keyword?: string): Observable<Event[]> {
    if (keyword) {
      return this.http.get<Event[]>(`${this.rootConst.SERVER_UPCOMING_EVENT_FILTER_BY_KEYWORD}${keyword}`);
    }
    return this.http.get<Event[]>(`${this.rootConst.SERVER_UPCOMING_EVENT}`);
  }

  enrollUser(user: User, event: Event): Observable<Event[]> {
    const body = JSON.stringify({eventID: event.idEvent, user: user});
    return this.http.post<Event[]>(this.rootConst.SERVER_ENROLL_USER, body, this.httpOptions);
  }
  unenrollUser(user: User, event: Event): Observable<Event[]> {
    const body = JSON.stringify({eventID: event.idEvent, user: user});
    return this.http.post<Event[]>(this.rootConst.SERVER_UNENROLL_USER, body, this.httpOptions);
  }
  getStatusEnrollmentForUser(user: User, event: Event): Observable<Boolean> {
    const body = JSON.stringify({eventID: event.idEvent, user: user});
    return this.http.post<Boolean>(this.rootConst.SERVER_IS_ENROLLED, body, this.httpOptions);
  }
}
