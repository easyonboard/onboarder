import {Component, OnInit, ViewEncapsulation, ViewChild, AfterContentChecked, AfterViewChecked} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {ActivatedRoute, Router} from '@angular/router';
import {PageEvent, MatTooltip} from '@angular/material';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TutorialsComponent implements OnInit, AfterViewChecked {

  private rootConst: RootConst;
  tutorials: TutorialDTO[];
  tutorialsPerPage: TutorialDTO[];
  pageEvent: PageEvent;
  length: number;
  pageSize = 10;
  pageSizeOptions = [5, 10, 25, 100];

  public popups_visible = true;

  public paginator_msg = 'choose the number of items per page & navigate through pages';
  public tutorial_msg = 'tutorial msg demo';
  public tutorial_title_msg = 'tutorial title msg demo';
  public tutorial_keywords_msg = 'tutorial keywords msg demo';
  public tutorial_delete_msg = 'tutorial delete msg demo';
  public tutorial_overview_msg = 'tutorial overview msg demo';

  @ViewChild('tooltipPaginator') tooltipPaginator: MatTooltip;
  @ViewChild('tooltipTitle') tooltipTitle: MatTooltip;
  @ViewChild('tooltipKeywords') tooltipKeywords: MatTooltip;
  @ViewChild('tooltipTutorial') tooltipTutorial: MatTooltip;
  @ViewChild('tooltipOverview') tooltipOverview: MatTooltip;
  @ViewChild('tooltipDelete') tooltipDelete: MatTooltip;

  constructor(private tutorialService: TutorialService,
              private route: ActivatedRoute,
              private router: Router) {
    this.rootConst = new RootConst();
    this.tutorialsPerPage = [];

    this.route.params.subscribe(params => {
      this.tutorialsPerPage = [];

      const keyword = params['keyword'];
      if (keyword) {
        this.tutorialService.searchByKeyword(keyword).subscribe(tutorials => {this.tutorials = tutorials;
          this.initTutorialsPerPageList();
          this.length = this.tutorials.length;
        });
      } else {
        tutorialService.getTutorials().subscribe(tutorials => {this.tutorials = tutorials;
          this.initTutorialsPerPageList();
          this.length = this.tutorials.length;
        });
      }

    });
  }

  ngOnInit() {
  }


  ngAfterViewChecked() {
    if (this.tooltipPaginator !== undefined && this.tooltipPaginator._isTooltipVisible() === false) {
      this.tooltipPaginator.show();
    }
    if (this.tooltipTitle !== undefined && this.tooltipTitle._isTooltipVisible() === false) {
      this.tooltipTitle.show();
    }
    if (this.tooltipKeywords !== undefined && this.tooltipKeywords._isTooltipVisible() === false) {
      this.tooltipKeywords.show();
    }
    if (this.tooltipOverview !== undefined && this.tooltipOverview._isTooltipVisible() === false) {
      this.tooltipOverview.show();
    }
    if (this.tooltipTutorial !== undefined && this.tooltipTutorial._isTooltipVisible() === false) {
      this.tooltipTutorial.show();
    }
    if (this.tooltipDelete !== undefined && this.tooltipDelete._isTooltipVisible() === false) {
      this.tooltipDelete.show();
    }
  }


  disablePopups(): void {
    this.popups_visible = false;
    this.tooltipPaginator.disabled = true;
    this.tooltipTitle.disabled = true;
    this.tooltipTutorial.disabled = true;
    this.tooltipKeywords.disabled = true;
    this.tooltipOverview.disabled = true;
    this.tooltipDelete.disabled = true;
  }

  addTutorialRouterLink(): void {
    location.replace(this.rootConst.FRONT_ADD_TUTORIAL);
  }


  searchByKeyword(keyword: string) {
    if (keyword !== 'addTutorialRouterLink') {
      this.router.navigate(['tutorials/keywords/' + keyword]);
    }
  }

  public getServerData(event?: PageEvent) {
    this.tutorialsPerPage = [];
    // tslint:disable-next-line:no-debugger
    debugger;

    let index = event.pageIndex;
    let pageSize = event.pageSize;
    let indexList = index * pageSize;
    for (indexList; indexList < (index + 1) * pageSize; indexList++) {
      // tslint:disable-next-line:curly
      if (this.tutorials[indexList] != null)
        this.tutorialsPerPage.push(this.tutorials[indexList]);
    }


  }
  public initTutorialsPerPageList() {
    for (let indexList = 0; indexList <  this.pageSize; indexList++) {
      // tslint:disable-next-line:curly
      if (this.tutorials[indexList] != null)
        this.tutorialsPerPage.push(this.tutorials[indexList]);
    }
  }

  deleteTutorial(idTutorial: number) {
    if (confirm('Do you want to delete this tutorial?')) {
      this.tutorialService.deleteTutorial(idTutorial).subscribe(tutorials => this.tutorialsPerPage = tutorials);
    }
  }
}
