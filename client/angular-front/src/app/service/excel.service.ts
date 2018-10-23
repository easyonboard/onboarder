import {Injectable} from '@angular/core';
import {UserDetailsToExport} from '../domain/user';


@Injectable()
export class ExcelService {

  constructor() {
  }

  public exportAsExcelFile(list: UserDetailsToExport[], excelFileName: string): void {
    const csvData = this.convertToCSV(list);
    const a = document.createElement('a');
    a.setAttribute('style', 'display:none;');
    document.body.appendChild(a);
    const blob = new Blob([csvData], {type: 'text/csv'});
    const url = window.URL.createObjectURL(blob);
    a.href = url;
    a.download = 'User_Results.csv';
    a.click();


  }

  convertToCSV(objArray) {
    const array = typeof objArray !== 'object' ? JSON.parse(objArray) : objArray;
    let str = '';
    let row = '';

    for (let index in objArray[0]) {
      row += index + ',';
    }
    row = row.slice(0, -1);
    str += row + '\r\n';

    for (let i = 0; i < array.length; i++) {
      let line = '';
      for (let index in array[i]) {
        if (line !== '') line += ',';
        {
          line += array[i][index];
        }
      }
      str += line + '\r\n';
    }
    return str;
  }
}
