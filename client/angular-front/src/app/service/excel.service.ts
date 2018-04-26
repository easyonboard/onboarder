import {Injectable} from '@angular/core';
import {UserDetailsToExport} from '../domain/user';


@Injectable()
export class ExcelService {

  constructor() {
  }

  public exportAsExcelFile(list: UserDetailsToExport[], excelFileName: string): void {
    var csvData = this.ConvertToCSV(list);
    var a = document.createElement("a");
    a.setAttribute('style', 'display:none;');
    document.body.appendChild(a);
    var blob = new Blob([csvData], {type: 'text/csv'});
    var url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = 'User_Results.csv';
    a.click();


  }

  ConvertToCSV(objArray) {
    var array = typeof objArray != 'object' ? JSON.parse(objArray) : objArray;
    var str = '';
    var row = "";

    debugger
    for (var index in objArray[0]) {
      row += index + ',';
    }
    row = row.slice(0, -1);
    str += row + '\r\n';

    for (var i = 0; i < array.length; i++) {
      var line = '';
      for (var index in array[i]) {
        if (line != '') line += ','
        {
          line += array[i][index];
        }
      }
      str += line + '\r\n';
    }
    return str;
  }
}
