import {Injectable, OnInit} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Material} from '../domain/material';
import { TutorialMaterialDTO } from '../domain/tutorialMaterial';
import {Observable} from 'rxjs/Observable';
import {RootConst} from '../util/RootConst';

import 'rxjs/Rx';

@Injectable()
export class MaterialService implements OnInit {

  private rootConst: RootConst = new RootConst();
  private addMaterialURL = this.rootConst.SERVER_ADD_MATERIAL;
  private addTutorialMaterialURL = this.rootConst.SERVER_ADD_TUTORIAL_MATERIAL;
  private findMaterialById = this.rootConst.SERVER_FIND_MATERIAL_BY_ID;
  private allMaterialsUploadedByThisUser = this.rootConst.SERVER_MATERIALS_UPLOADED_BY_USE;
  private getMaterialsFromSubject: string= this.rootConst.SERVER_MATERIALS_FROM_SUBJECT;

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };

  constructor(private http: HttpClient) {
  }

  addMaterial(material: Material, file: File, idSubject: number) {
    var formData = new FormData();
    formData.append('material', JSON.stringify(material));
    formData.append('file', file);
    formData.append('idSubject', JSON.stringify(idSubject));

    var request = new XMLHttpRequest();
    request.open('POST', `${this.addMaterialURL}`);
    return request.send(formData);
  }

  addTutorialMaterial(material: TutorialMaterialDTO, file: File, idTutorial: number) {
    // var formData = new FormData();
    // formData.append('material', JSON.stringify(material));
    // formData.append('file', file);
    // formData.append('idTutorial', JSON.stringify(idTutorial));

    let body = JSON.stringify({material: material, file: file, idTutorial: idTutorial});
    return this.http.post<TutorialMaterialDTO>(this.rootConst.SERVER_ADD_TUTORIAL_MATERIAL, body, this.httpOptions);

    // var request = new XMLHttpRequest();
    // request.open('POST', `${this.addTutorialMaterialURL}`);
    // return request.send(formData);
  }

  allMaterialsAddedByUser(username: String): Observable<Material[]> {
    return this.http.get<Material[]>(`${this.allMaterialsUploadedByThisUser}${username}`);
  }

  getFileWithId(idMaterial: number): any {
    return this.http.get(`${this.findMaterialById}${idMaterial}`, {responseType: 'arraybuffer'}).subscribe(
      (response) => {
        var file = new Blob([response], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      });
  }

  addMaterialsToSubject(idSubject: number, materialForCurrentSubject: Material, file: File) {
    // for (var index = 0; index < materialsForCurrentSubject.length; index++) {
    //   this.addMaterial(materialsForCurrentSubject[index], files[index], idSubject);
    // }
    this.addMaterial(materialForCurrentSubject, file, idSubject);

  }

  ngOnInit(): void {
  }

  getMaterialsFromSubjectId(idSubject: number) {
    return this.http.get<Material[]>(`${this.getMaterialsFromSubject}${idSubject}`);
  }
}
