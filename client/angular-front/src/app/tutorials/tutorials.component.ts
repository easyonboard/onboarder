import {Component, OnInit} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {TutorialMaterialDTO} from '../domain/tutorialMaterial';
import {forEach} from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css']
})
export class TutorialsComponent implements OnInit {

  private rootConst: RootConst;
  tutorials: TutorialDTO[];

  constructor(private tutorialService: TutorialService) {
    this.rootConst = new RootConst();
    tutorialService.getTutorials().subscribe(tutorials => this.tutorials = tutorials);
  }

  ngOnInit() {
  }

  addTutorial(): void {
    location.replace(this.rootConst.FRONT_ADD_TUTORIAL);
  }

  downloadFile(materials: TutorialMaterialDTO[]): void {
    materials.forEach(material => this.tutorialService.getFileWithId(material.idTutorialMaterial));
  }


}
