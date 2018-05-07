import { Component, OnInit } from '@angular/core';
import { RootConst } from '../util/RootConst';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css']
})
export class TutorialsComponent implements OnInit {

  private rootConst: RootConst;

  constructor() {
    this.rootConst = new RootConst();
  }

  ngOnInit() {
  }

  addTutorial(): void {
    location.replace(this.rootConst.FRONT_ADD_TUTORIAL);
  }


}
