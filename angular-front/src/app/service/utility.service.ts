import {Injectable} from '@angular/core';

@Injectable()
export class UtilityService {

  constructor() {
  }

  openModal(id: string): void {
    var modal = document.getElementById(id);

    modal.style.display = "block";

  }

  closeModal(myModal: string): void {

    var modal = document.getElementById(myModal);
    modal.style.display = "none";

  }

}
