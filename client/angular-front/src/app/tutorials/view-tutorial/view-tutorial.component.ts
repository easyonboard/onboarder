import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {MatTooltip} from '@angular/material';
import {TutorialDTO} from '../../domain/tutorial';
import {MaterialType} from '../../domain/materialType';
import {TutorialService} from '../../service/tutorial.service';
import {MaterialService} from '../../service/material.service';
import {TutorialMaterialDTO} from '../../domain/tutorialMaterial';

@Component({
  selector: 'app-view-tutorial',
  templateUrl: './view-tutorial.component.html',
  styleUrls: ['./view-tutorial.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ViewTutorialComponent implements OnInit {
  private tutorialId: number;
  public tutorial: TutorialDTO;

  public tutorial_title_msg = 'tutorial title msg demo';
  public tutorial_keywords_msg = 'tutorial keywords msg demo';
  public tutorial_contact_msg = 'Here are the informations about the contact person/s for this tutorial';
  public tutorial_overview_msg = 'tutorial overview msg demo';

  constructor(private route: ActivatedRoute,
              private router: Router,
              private tutorialService: TutorialService,
              private materialService: MaterialService) {
  }

  ngOnInit() {
    this.tutorialId = this.route.snapshot.params.id;
    this.tutorialService.getTutorialWithId(this.tutorialId).subscribe(tutorial => {
      this.tutorial = tutorial;
    });
  }

  searchByKeyword(keyword: string) {
    if (keyword !== 'addTutorialRouterLink') {
      this.router.navigate(['tutorials/keywords/' + keyword]);
    }
  }

  getFileWithId(idFile: number) {
    this.tutorialService.getFileWithId(idFile).subscribe((response) => {
      const file = new Blob([response], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

  addScollBarToPage() {
    debugger
    document.getElementById('tutorialDetails').style.overflow = 'scroll';
  }

}
