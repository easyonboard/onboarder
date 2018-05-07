import { Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { RootConst } from '../util/RootConst';
import { TutorialDTO } from '../domain/tutorial';

@Injectable()
export class TutorialService implements OnInit {

  private rootConst: RootConst = new RootConst();

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
  }

  addTutorial(tutorial: TutorialDTO, ownersIds: number[], contactPersonsIds: number[]): any {
    let body = JSON.stringify({tutorial: tutorial, ownersIds: ownersIds, contactPersonsId: contactPersonsIds});
    return this.http.post<TutorialDTO>(this.rootConst.SERVER_ADD_TUTORIAL, body, this.httpOptions);
  }


}
