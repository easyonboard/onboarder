import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Material} from '../domain/material';
import {TutorialMaterialDTO} from '../domain/tutorialMaterial';
import {Observable} from 'rxjs/Observable';
import {RootConst} from '../util/RootConst';
import 'rxjs/Rx';

@Injectable()
export class MaterialService {

  private rootConst: RootConst = new RootConst();

  constructor(private http: HttpClient) {
  }

  addMaterialToTutorial(material: TutorialMaterialDTO, file: File, idTutorial: number): any {
    const formData = new FormData();
    formData.append('material', JSON.stringify(material));
    formData.append('file', file);
    formData.append('idTutorial', JSON.stringify(idTutorial));

    const request = new XMLHttpRequest();
    request.open('POST', `${this.rootConst.SERVER_ADD_TUTORIAL_MATERIAL}`);
    return request.send(formData);
  }

  getFileWithId(idTutorialMaterial: number): Observable<ArrayBuffer> {
    return this.http.get(`${this.rootConst.SERVER_FIND_TUTORIAL_MATERIAL_BY_ID}${idTutorialMaterial}`, {responseType: 'arraybuffer'});
  }


  deleteMaterialWithId(idMaterial: number): Observable<any> {
    return this.http.get(`${this.rootConst.SERVER_DELETE_MATERIAL}${idMaterial}`);
  }
}
