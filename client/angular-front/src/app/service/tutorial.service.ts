import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {ServerURLs} from '../util/ServerURLs';
import {Tutorial} from '../domain/tutorial';
import {Observable} from 'rxjs/Observable';
import {Material} from '../domain/tutorialMaterial';

@Injectable()
export class TutorialService implements OnInit {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  addTutorial(tutorial: Tutorial, contactPersons: String[]): any {
    const contactPersonMsgMails: String[] = [];

    contactPersons.forEach(cp => {
      contactPersonMsgMails.push(cp);
    });

    const body = JSON.stringify({tutorial: tutorial, contactPersonMsgMails: contactPersonMsgMails});
    return this.http.post<Tutorial>(ServerURLs.ADD_TUTORIAL, body, this.httpOptions);
  }

  getTutorials(keyword?: string): Observable<Tutorial[]> {
    if (keyword) {
      return this.http.get<Tutorial[]>(`${ServerURLs.SEARCH_TUTORIAL_BY_KEYWORD}${keyword}`);
    }
    return this.http.get<Tutorial[]>(`${ServerURLs.ALL_TUTORIALS}`);
  }

  getMaterialsForTutorialId(idTutorial: number): Observable<Material[]> {
    return this.http.get<Material[]>(`${ServerURLs.GET_MATERIALS_FOR_TUTORIAL}${idTutorial}`);
  }

  searchByKeyword(keyword: string): Observable<Tutorial[]> {
    return this.http.get<Tutorial[]>(`${ServerURLs.SEARCH_TUTORIAL_BY_KEYWORD}${keyword}`);
  }

  getTutorialWithId(tutorialId: number): Observable<Tutorial> {
    return this.http.get<Tutorial>(`${ServerURLs.SEARCH_TUTORIAL_BY_ID}${tutorialId}`);
  }

  deleteTutorial(idTutorial: number): Observable<Tutorial[]> {
    const body = JSON.stringify({idTutorial: idTutorial});
    return this.http.post<Tutorial[]>(ServerURLs.DELETE_TUTORIAL, body, this.httpOptions);
  }

}
