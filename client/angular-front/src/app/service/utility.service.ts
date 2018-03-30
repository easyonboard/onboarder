import { Injectable } from '@angular/core';

@Injectable()
export class UtilityService {

  constructor() {
  }

  openModal(id: string): void {
    let modal = document.getElementById(id);

    modal.style.display = 'block';

  }

  closeModal(myModal: string): void {

    let modal = document.getElementById(myModal);
    modal.style.display = 'none';

  }

}
