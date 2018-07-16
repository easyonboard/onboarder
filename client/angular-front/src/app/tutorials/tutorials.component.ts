import {Component, OnInit, ViewEncapsulation, ViewChild, AfterContentChecked, AfterViewChecked, OnDestroy} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {PageEvent, MatTooltip} from '@angular/material';
import {TooltipConst} from '../util/TooltipConst';
import {LocalStorageConst} from '../util/LocalStorageConst';
import {Subscription} from 'rxjs/Subscription';
import {Http} from '@angular/http';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TutorialsComponent implements AfterViewChecked, OnDestroy, OnInit {

  private rootConst: RootConst;
  private tooltips: TooltipConst = new TooltipConst();

  tutorials: TutorialDTO[];
  tutorialsPerPage: TutorialDTO[];
  pageEvent: PageEvent;
  pageSize = 9;
  pageSizeOptions = [9, 18, 36, 99];

  public paginator_msg = this.tooltips.PAGINATOR_MSG;
  public tutorial_msg = this.tooltips.TUTORIAL_MSG;
  public tutorial_title_msg = this.tooltips.TUTORIAL_TITLE_MSG;
  public tutorial_keywords_msg = this.tooltips.TUTORIAL_KEYWORDS_MSG;
  public tutorial_delete_msg = this.tooltips.TUTORIAL_DELETE_MSG;
  public tutorial_overview_msg = this.tooltips.TUTORIAL_OVERVIEW_MSG;

  public show;
  private httpSubscription: Subscription;

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

    this.show = LocalStorageConst.IS_DEMO_ENABLED;
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.route.queryParams.subscribe(
        queryParams => {
          let pageIndex = +queryParams['page'];
          if (!pageIndex) {
            pageIndex = 0;
          }
          this.httpSubscription = this.decision(params).subscribe(tutorials => {
            this.tutorials = tutorials;
            this.initTutorialsPerPageList(this.pageSize, pageIndex);
          });
        }
      );
    });
  }

  ngAfterViewChecked() {
    this.show = LocalStorageConst.IS_DEMO_ENABLED;

    if (this.tooltipPaginator !== undefined) {
      if (this.show === true) {
        this.tooltipPaginator.disabled = false;
      } else {
        this.tooltipPaginator.disabled = true;
      }
    }
    if (this.tooltipTitle !== undefined) {
      if (this.show === true) {
        this.tooltipTitle.disabled = false;
      } else {
        this.tooltipTitle.disabled = true;
      }
    }
    if (this.tooltipKeywords !== undefined) {
      if (this.show === true) {
        this.tooltipKeywords.disabled = false;
      } else {
        this.tooltipKeywords.disabled = true;
      }
    }
    if (this.tooltipOverview !== undefined) {
      if (this.show === true) {
        this.tooltipOverview.disabled = false;
      } else {
        this.tooltipOverview.disabled = true;
      }
    }
    if (this.tooltipTutorial !== undefined) {
      if (this.show === true) {
        this.tooltipTutorial.disabled = false;
      } else {
        this.tooltipTutorial.disabled = true;
      }
    }
    if (this.tooltipDelete !== undefined) {
      if (this.show === true) {
        this.tooltipDelete.disabled = false;
      } else {
        this.tooltipDelete.disabled = true;
      }
    }
  }

  ngOnDestroy(): void {
    this.httpSubscription.unsubscribe();
  }

  private decision(params: any): any {
    const keyword = params['keyword'];

    if (keyword) {
      return this.tutorialService.searchByKeyword(keyword);
    } else if (this.router.url.indexOf('draft') >= 0) {
      const userId = +localStorage.getItem('userLoggedId');
      return this.tutorialService.getDraftsTutorialsForUser(userId);
    } else {
      return this.tutorialService.getTutorials();
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
    const queryParams: Params = Object.assign({}, this.route.snapshot.queryParams);

    queryParams['page'] = event.pageIndex;
    let root = '/';
    this.route.snapshot.pathFromRoot.forEach(el => root = root.concat(el.url.toString()));
    this.router.navigate([root], {queryParams: queryParams});
  }

  public initTutorialsPerPageList(pageSize: number, pageIndex: number) {
    this.tutorialsPerPage = [];
    this.tutorialsPerPage = this.tutorials.slice(pageIndex * pageSize, ((+pageIndex + 1) * pageSize));
  }

  deleteTutorial(idTutorial: number) {
    if (confirm('Do you want to delete this tutorial?')) {
      this.tutorialService.deleteTutorial(idTutorial).subscribe(tutorials => this.tutorialsPerPage = tutorials);
    }
  }
}
