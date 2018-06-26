import {Component, OnInit, ViewEncapsulation, AfterViewChecked, ViewChild} from '@angular/core';
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
export class ViewTutorialComponent implements OnInit, AfterViewChecked {
  private tutorialId: number;
  public tutorial: TutorialDTO;

  public tutorial_title_msg = 'Tutorial\'s title';
  public tutorial_keywords_msg = 'Some keywords to help easily identify the subject';
  public tutorial_contact_msg = 'Here are the informations about the contact person/s for this tutorial';
  public tutorial_overview_msg = 'This is the overview';
  public tutorial_material_msg = 'Here is the material associated with the tutorial ';

  public popups_visible = true;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private tutorialService: TutorialService,
              private materialService: MaterialService) {
  }

  @ViewChild('tooltipTitle') tooltipTitle: MatTooltip;
  @ViewChild('tooltipKeywords') tooltipKeywords: MatTooltip;
  @ViewChild('tooltipContact') tooltipContact: MatTooltip;
  @ViewChild('tooltipOverview') tooltipOverview: MatTooltip;
  @ViewChild('tooltipMaterial') tooltipMaterial: MatTooltip;

  ngAfterViewChecked() {
    if (this.tooltipTitle !== undefined && this.tooltipTitle._isTooltipVisible() === false) {
      this.tooltipTitle.show();
    }
    if (this.tooltipKeywords !== undefined && this.tooltipKeywords._isTooltipVisible() === false) {
      this.tooltipKeywords.show();
    }
    if (this.tooltipOverview !== undefined && this.tooltipOverview._isTooltipVisible() === false) {
      this.tooltipOverview.show();
    }
    if (this.tooltipContact !== undefined && this.tooltipContact._isTooltipVisible() === false) {
      this.tooltipContact.show();
    }
    if (this.tooltipMaterial !== undefined && this.tooltipMaterial._isTooltipVisible() === false) {
      this.tooltipMaterial.show();
    }
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

  disablePopups(): void {
    this.popups_visible = false;
    this.tooltipTitle.disabled = true;
    this.tooltipKeywords.disabled = true;
    this.tooltipOverview.disabled = true;
    this.tooltipContact.disabled = true;
    this.tooltipMaterial.disabled = true;
  }

  addScollBarToPage() {
    // tslint:disable-next-line:no-debugger
    debugger;
    document.getElementById('tutorialDetails').style.overflow = 'scroll';
  }

}
