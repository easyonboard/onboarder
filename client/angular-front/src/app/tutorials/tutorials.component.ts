import {Component, OnInit, ViewEncapsulation, ViewChild, AfterContentChecked, AfterViewChecked} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {ActivatedRoute, Router} from '@angular/router';
import {PageEvent, MatTooltip} from '@angular/material';
import { TooltipConst } from '../util/TooltipConst';
import { LocalStorageConst } from '../util/LocalStorageConst';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TutorialsComponent implements AfterViewChecked {

  private rootConst: RootConst;
  private tooltips: TooltipConst = new TooltipConst();

  tutorials: TutorialDTO[];
  tutorialsPerPage: TutorialDTO[];
  pageEvent: PageEvent;
  length: number;
  pageSize = 10;
  pageSizeOptions = [5, 10, 25, 100];

  public paginator_msg = this.tooltips.PAGINATOR_MSG;
  public tutorial_msg = this.tooltips.TUTORIAL_MSG;
  public tutorial_title_msg = this.tooltips.TUTORIAL_TITLE_MSG;
  public tutorial_keywords_msg = this.tooltips.TUTORIAL_KEYWORDS_MSG;
  public tutorial_delete_msg = this.tooltips.TUTORIAL_DELETE_MSG;
  public tutorial_overview_msg = this.tooltips.TUTORIAL_OVERVIEW_MSG;

  public show;

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

    this.show = LocalStorageConst.IS_DEMO_ENABLED;

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

  ngAfterViewChecked() {
    this.show = LocalStorageConst.IS_DEMO_ENABLED;

    if (this.tooltipPaginator !== undefined && this.show === true) {
      this.tooltipPaginator.disabled = false;
    } else {
      this.tooltipPaginator.disabled = true;
    }
    if (this.tooltipTitle !== undefined && this.show === true) {
      this.tooltipTitle.disabled = false;
    } else {
      this.tooltipTitle.disabled = true;
    }
    if (this.tooltipKeywords !== undefined && this.show === true) {
      this.tooltipKeywords.disabled = false;
    } else {
      this.tooltipKeywords.disabled = true;
    }
    if (this.tooltipOverview !== undefined && this.show === true) {
      this.tooltipOverview.disabled = false;
    } else {
      this.tooltipOverview.disabled = true;
    }
    if (this.tooltipTutorial !== undefined && this.show === true) {
      this.tooltipTutorial.disabled = false;
    } else {
      this.tooltipTutorial.disabled = true;
    }
    if (this.tooltipDelete !== undefined && this.show === true) {
      this.tooltipDelete.disabled = false;
    } else {
      this.tooltipDelete.disabled = true;
    }
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
