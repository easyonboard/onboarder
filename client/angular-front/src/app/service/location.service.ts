import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {Course} from '../domain/course';
import {Observer} from 'rxjs/Observer';
import {Observable} from 'rxjs/Observable';
import {TutorialMaterialDTO} from '../domain/tutorialMaterial';
import {LocationDTO} from '../domain/location';
import {TutorialDTO} from '../domain/tutorial';

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
}
