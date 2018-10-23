import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Material} from '../domain/tutorialMaterial';
import {Observable} from 'rxjs/Observable';
import {TokenStorage} from '../common/core-auth/token.storage';
import {ServerURLs} from '../util/ServerURLs';
import 'rxjs';

@Injectable()
export class MaterialService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {
  }

  addMaterialToTutorial(material: Material, file: File, idTutorial: number): any {
    const formData = new FormData();
    formData.append('material', JSON.stringify(material));
    formData.append('file', file);
    formData.append('idTutorial', JSON.stringify(idTutorial));
    console.log(formData);

    const request = new XMLHttpRequest();
    request.open('POST', `${ServerURLs.ADD_MATERIAL}`);
    request.setRequestHeader('authorization', this.tokenStorage.getTokenBearerString() + this.tokenStorage.getToken());
    return request.send(formData);
  }

  getFileWithId(idTutorialMaterial: number): Observable<ArrayBuffer> {
    return this.http.get(`${ServerURLs.FIND_MATERIAL_BY_ID}${idTutorialMaterial}`, {responseType: 'arraybuffer'});
  }


  deleteMaterialWithId(idMaterial: number): Observable<any> {
    return this.http.get(`${ServerURLs.DELETE_MATERIAL}${idMaterial}`);
  }
}
