import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {ServerURLs} from '../util/ServerURLs';
import {Observable} from 'rxjs/Observable';
import {Location} from '../domain/location';
import {MeetingHall} from '../domain/event';
import {debug} from 'util';

@Injectable()
export class LocationService implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  getLocations(): Observable<Location[]> {
    return this.http.get<Location[]>(`${ServerURLs.ALL_LOCATIONS}`);
  }

  getRooms(): Observable<MeetingHall[]> {
    return this.http.get<MeetingHall[]>(`${ServerURLs.ALL_ROOMS}`);
  }
}
