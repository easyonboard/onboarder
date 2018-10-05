import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Material} from '../domain/tutorialMaterial';
import {Observable} from 'rxjs/Observable';
import {RootConst} from '../util/RootConst';
import 'rxjs';
import {TokenStorage} from '../common/core-auth/token.storage';

@Injectable()
export class MaterialService {

  private rootConst: RootConst = new RootConst();

  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {
  }

  addMaterialToTutorial(material: Material, file: File, idTutorial: number): any {
    const formData = new FormData();
    formData.append('material', JSON.stringify(material));
    formData.append('file', file);
    formData.append('idTutorial', JSON.stringify(idTutorial));
    console.log(formData);

    const request = new XMLHttpRequest();
    request.open('POST', `${this.rootConst.SERVER_ADD_TUTORIAL_MATERIAL}`);
    request.setRequestHeader('authorization', this.tokenStorage.getTokenBearerString() + this.tokenStorage.getToken());
    return request.send(formData);
  }

  getFileWithId(idTutorialMaterial: number): Observable<ArrayBuffer> {
    return this.http.get(`${this.rootConst.SERVER_FIND_TUTORIAL_MATERIAL_BY_ID}${idTutorialMaterial}`, {responseType: 'arraybuffer'});
  }


  deleteMaterialWithId(idMaterial: number): Observable<any> {
    return this.http.get(`${this.rootConst.SERVER_DELETE_MATERIAL}${idMaterial}`);
  }
}
