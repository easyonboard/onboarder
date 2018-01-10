import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Material} from "../domain/material";
import {RootConst} from "../util/RootConst";
import 'rxjs/Rx';
import {DomSanitizer} from "@angular/platform-browser";
import {ResponseContentType} from "@angular/http";

@Injectable()
export class MaterialService implements OnInit {

  private rootConst: RootConst = new RootConst();
  private addMaterialURL = this.rootConst.SERVER_ADD_MATERIAL;
  private findMaterialById = this.rootConst.SERVER_FIND_MATERIAL_BY_ID;

  constructor(private http: HttpClient) {
  }

  addMaterial(material: Material, file: File) {
    var formData = new FormData();
    formData.append('material', JSON.stringify(material))
    formData.append('file', file);

    var request = new XMLHttpRequest();
    request.open('POST', `${this.addMaterialURL}`);
    return request.send(formData);
  }

  getFileWithId(idMaterial: number, tiltleMaterial: string): any {
    return this.http.get(`${this.findMaterialById}${idMaterial}`, {responseType: 'arraybuffer'}).subscribe(
      (response) => {
        var file = new Blob([response], {type: 'application/pdf'});
        var fileURL = URL.createObjectURL(file);
        window.open(fileURL);
      });
  }

  ngOnInit(): void {
  }


}
