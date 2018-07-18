import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {Observable} from 'rxjs/Observable';
import {TutorialMaterialDTO} from '../domain/tutorialMaterial';
import { ContactPersonDto } from '../domain/user';

@Injectable()
export class TutorialService implements OnInit {

  private rootConst: RootConst = new RootConst();

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  /** !TODO */
  addTutorial(tutorial: TutorialDTO, contactPersons: String[]): any {
    // tslint:disable-next-line:prefer-const
    let contactPersonsDto: ContactPersonDto[] = [];
    // tslint:disable-next-line:prefer-const
    let contactPersonDto: ContactPersonDto = new ContactPersonDto;

    contactPersons.forEach(cp => {
      const x = cp.split(/[()]/);
      contactPersonDto.username = x[0];
      contactPersonDto.msgEmail = x[1];
      contactPersonsDto.push(contactPersonDto);
    });

    const body = JSON.stringify({tutorial: tutorial, contactPersonsDto: contactPersonsDto});
    return this.http.post<TutorialDTO>(this.rootConst.SERVER_ADD_TUTORIAL, body, this.httpOptions);
  }

  getTutorials(): Observable<TutorialDTO[]> {
    return this.http.get<TutorialDTO[]>(`${this.rootConst.SERVER_GET_TUTORIAL}`);
  }


  getMaterialsForTutorialId(idTutorial: number): Observable<TutorialMaterialDTO[]> {
    return this.http.get<TutorialMaterialDTO[]>(`${this.rootConst.SERVER_GET_MATERIALS_FOR_TUTORIAL}${idTutorial}`);
  }

  searchByKeyword(keyword: string): Observable<TutorialDTO[]> {
    return this.http.get<TutorialDTO[]>(`${this.rootConst.SERVER_SEARCH_TUTORIAL_BY_KEYWORD}${keyword}`);
  }

  getTutorialWithId(tutorialId: number): Observable<TutorialDTO> {
    return this.http.get<TutorialDTO>(`${this.rootConst.SERVER_SEARCH_TUTORIAL_BY_ID}${tutorialId}`);
  }

  deleteTutorial(idTutorial: number): Observable<TutorialDTO[]> {
    const body = JSON.stringify({idTutorial: idTutorial});
    return this.http.post<TutorialDTO[]>(this.rootConst.SERVER_DELETE_TUTORIAL, body, this.httpOptions);

  }

  updateTutorial(tutorial: TutorialDTO, contactPersons: number[]) {
    const body = JSON.stringify({tutorial: tutorial, contactPersons: contactPersons});
    return this.http.post<TutorialDTO>(this.rootConst.SERVER_UPADATE_TUTORIAL, body, this.httpOptions);

  }

  getDraftsTutorialsForUser(userId: number) {
    return this.http.get<TutorialDTO[]>(`${this.rootConst.SERVER_GET_DRAFTS_TUTORIAL}${userId}`);
  }
}
