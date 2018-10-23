import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Tutorial} from '../../domain/tutorial';
import {TutorialService} from '../../service/tutorial.service';
import {MaterialService} from '../../service/material.service';
import {forEach} from '@angular/router/src/utils/collection';

@Component({
  selector: 'app-view-tutorial',
  templateUrl: './view-tutorial.component.html',
  styleUrls: ['./view-tutorial.component.css']
})
export class ViewTutorialComponent implements OnInit {
  private tutorialId: number;
  public tutorial: Tutorial;
  showInfo = false;

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

  getFileWithId(idFile: number) {
    this.materialService.getFileWithId(idFile).subscribe((response) => {
      const file = new Blob([response], {type: 'application/pdf'});
      const fileURL = URL.createObjectURL(file);
      window.open(fileURL);
    });
  }

  addScollBarToPage() {
    document.getElementById('tutorialDetails').style.overflow = 'scroll';
  }

  openURL(link: string) {
    window.open(link, '_blank');
  }

  displayInfo() {
    this.showInfo = !this.showInfo;
  }
}
