import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {Course} from '../domain/course';
import {Observer} from 'rxjs/Observer';
import {Observable} from 'rxjs/Observable';
import {TutorialMaterialDTO} from '../domain/tutorialMaterial';

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

  addTutorial(tutorial: TutorialDTO, contactPersons: any[]): any {
    let body = JSON.stringify({tutorial: tutorial, contactPersons: contactPersons});
    return this.http.post<TutorialDTO>(this.rootConst.SERVER_ADD_TUTORIAL, body, this.httpOptions);
  }

  getTutorials(): Observable<TutorialDTO[]> {
    return this.http.get<TutorialDTO[]>(`${this.rootConst.SERVER_GET_TUTORIAL}`);
  }

  getFileWithId(idTutorialMaterial: number) {
    return this.http.get(`${this.rootConst.SERVER_FIND_TUTORIAL_MATERIAL_BY_ID}${idTutorialMaterial}`, {responseType: 'arraybuffer'}).subscribe(
      (response) => {
        const file = new Blob([response], {type: 'application/pdf'});
        const fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      });
  }


  getMaterialsForTutorialId(idTutorial: number): Observable<TutorialMaterialDTO[]> {
    return this.http.get<TutorialMaterialDTO[]>(`${this.rootConst.SERVER_GET_MATERIALS_FOR_TUTORIAL}${idTutorial}`);
  }

  searchByKeyword(keyword: string): Observable<TutorialDTO[]> {
    return this.http.get<TutorialDTO[]>(`${this.rootConst.SERVER_SEARCH_TUTORIAL_BY_KEYWORD}${keyword}`);
  }
}
