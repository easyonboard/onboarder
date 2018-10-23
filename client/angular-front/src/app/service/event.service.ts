import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {ServerURLs} from '../util/ServerURLs';
import {Observable} from 'rxjs/Observable';
import {Event, MeetingHall} from '../domain/event';
import {User} from '../domain/user';
import {Location} from '../domain/location';

@Injectable()
export class EventService implements OnInit {

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
    return this.http.post<Event>(ServerURLs.ADD_EVENT, body, this.httpOptions);
  }

  getPastEvents(keyword?: string): Observable<Event[]> {
    if (keyword) {
      return this.http.get<Event[]>(`${ServerURLs.PAST_EVENT_FILTER_BY_KEYWORD}${keyword}`);
    }
    return this.http.get<Event[]>(`${ServerURLs.PAST_EVENT}`);
  }

  getUpcomingEvents(keyword?: string): Observable<Event[]> {
    if (keyword) {
      return this.http.get<Event[]>(`${ServerURLs.UPCOMING_EVENT_FILTER_BY_KEYWORD}${keyword}`);
    }
    return this.http.get<Event[]>(`${ServerURLs.UPCOMING_EVENT}`);
  }

  enrollUser(user: User, event: Event): Observable<Event[]> {
    const body = JSON.stringify({eventID: event.idEvent, user: user});
    return this.http.post<Event[]>(ServerURLs.ENROLL_USER, body, this.httpOptions);
  }
  unenrollUser(user: User, event: Event): Observable<Event[]> {
    const body = JSON.stringify({eventID: event.idEvent, user: user});
    return this.http.post<Event[]>(ServerURLs.UNENROLL_USER, body, this.httpOptions);
  }
  getStatusEnrollmentForUser(user: User, event: Event): Observable<Boolean> {
    const body = JSON.stringify({eventID: event.idEvent, user: user});
    return this.http.post<Boolean>(ServerURLs.SERVER_IS_ENROLLED, body, this.httpOptions);
  }


  deleteUpcomingEvent(idEvent: number) {
    const body = JSON.stringify({idEvent: idEvent});
    return this.http.post<Event[]>(this.rootConst.SERVER_DELETE_UPCOMING_EVENT, body, this.httpOptions);
  }

  deletePastEvent(idEvent: number) {
    const body = JSON.stringify({idEvent: idEvent});
    return this.http.post<Event[]>(this.rootConst.SERVER_DELETE_PAST_EVENT, body, this.httpOptions);
  }
}
