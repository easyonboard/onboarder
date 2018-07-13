import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {Observable} from 'rxjs/Observable';
import {LocationDTO} from '../domain/location';
import {MeetingHall} from '../domain/event';

@Injectable()
export class LocationService implements OnInit {

  private rootConst: RootConst = new RootConst();

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  getLocations(): Observable<LocationDTO[]> {
    return this.http.get<LocationDTO[]>(`${this.rootConst.WEB_SERVER_LOCATIONS}`);
  }

  getRooms():Observable<MeetingHall[]> {
    return this.http.get<MeetingHall[]>(`${this.rootConst.WEB_SERVER_ROOMS}`);
  }
}
