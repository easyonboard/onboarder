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
  private addMaterialURL = this.rootConst.SERVER_ADD_MATERIAL;
  private addTutorialMaterialURL = this.rootConst.SERVER_ADD_TUTORIAL_MATERIAL;
  private allMaterialsUploadedByThisUser = this.rootConst.SERVER_MATERIALS_UPLOADED_BY_USE;
  private getMaterialsFromSubject: string = this.rootConst.SERVER_MATERIALS_FROM_SUBJECT;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }


  addMaterial(material: Material, file: File, idSubject: number) {
    const formData = new FormData();
    formData.append('material', JSON.stringify(material));
    formData.append('file', file);
    formData.append('idSubject', JSON.stringify(idSubject));

    const request = new XMLHttpRequest();
    request.open('POST', `${this.addMaterialURL}`);
    return request.send(formData);
  }

  addMaterialToTutorial(material: TutorialMaterialDTO, file: File, idTutorial: number): any {
    const formData = new FormData();
    formData.append('material', JSON.stringify(material));
    formData.append('file', file);
    formData.append('idTutorial', JSON.stringify(idTutorial));

    const request = new XMLHttpRequest();
    request.open('POST', `${this.addTutorialMaterialURL}`);
    return request.send(formData);
  }

  allMaterialsAddedByUser(username: String): Observable<Material[]> {
    return this.http.get<Material[]>(`${this.allMaterialsUploadedByThisUser}${username}`);
  }

  getFileWithId(idTutorialMaterial: number): Observable<ArrayBuffer> {
    return this.http.get(`${this.rootConst.SERVER_FIND_TUTORIAL_MATERIAL_BY_ID}${idTutorialMaterial}`, {responseType: 'arraybuffer'});
  }

  addMaterialsToSubject(idSubject: number, materialForCurrentSubject: Material, file: File) {
    // for (const index = 0; index < materialsForCurrentSubject.length; index++) {
    //   this.addMaterial(materialsForCurrentSubject[index], files[index], idSubject);
    // }
    this.addMaterial(materialForCurrentSubject, file, idSubject);

  }


  getMaterialsFromSubjectId(idSubject: number) {
    return this.http.get<Material[]>(`${this.getMaterialsFromSubject}${idSubject}`);
  }


  deleteMaterialWithId(idMaterial: number): Observable<any> {
    return this.http.get(`${this.rootConst.SERVER_DELETE_MATERIAL}${idMaterial}`);
  }
}
