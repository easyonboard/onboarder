import {Component, OnDestroy, OnInit} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {TutorialMaterialDTO} from '../domain/tutorialMaterial';
import {forEach} from '@angular/router/src/utils/collection';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css']
})
export class TutorialsComponent implements OnInit {

  private rootConst: RootConst;
  tutorials: TutorialDTO[];

  constructor(private tutorialService: TutorialService,
              private route: ActivatedRoute,
              private router: Router) {
    this.rootConst = new RootConst();
    this.route.params.subscribe(params => {
      const keyword = params['keyword'];
      if (keyword) {
        this.tutorialService.searchByKeyword(keyword).subscribe(tutorials => this.tutorials = tutorials);
      } else {
        tutorialService.getTutorials().subscribe(tutorials => this.tutorials = tutorials);
      }
    });
  }

  ngOnInit() {
  }

  addTutorial(): void {
    location.replace(this.rootConst.FRONT_ADD_TUTORIAL);
  }

  downloadFile(materials: TutorialMaterialDTO[]): void {
    materials.forEach(material => this.tutorialService.getFileWithId(material.idTutorialMaterial));
  }

  searchByKeyword(keyword: string) {
    if (keyword !== 'addTutorial') {
      this.router.navigate(['tutorials/keywords/' + keyword]);
    }
  }


}
