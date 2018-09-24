import {Component, OnDestroy, OnInit, ViewEncapsulation} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {MatSnackBar, PageEvent} from '@angular/material';
import {Subscription} from 'rxjs/Subscription';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TutorialsComponent implements OnDestroy, OnInit {

  private rootConst: RootConst;

  tutorials: TutorialDTO[];
  tutorialsPerPage: TutorialDTO[];
  pageEvent: PageEvent;
  pageSize = 9;
  pageSizeOptions = [9, 18, 36, 99];
  noDraftsMessage: string;

  private httpSubscription: Subscription;
  private pageIndex = 0;

  constructor(private tutorialService: TutorialService,
              private route: ActivatedRoute,
              private router: Router, private snackBar: MatSnackBar) {
    this.rootConst = new RootConst();
  }

  ngOnInit(): void {
    this.noDraftsMessage = '';
    this.route.queryParams.subscribe(
      queryParams => {
        this.pageIndex = +queryParams['page'];
        if (!this.pageIndex) {
          this.pageIndex = 0;
        }
        this.pageSize = +queryParams['pageSize'];
        if (!this.pageSize) {
          this.pageSize = 9;
        }

        this.httpSubscription = this.decision(queryParams).subscribe(tutorials => {
            this.tutorials = tutorials;
            this.initTutorialsPerPageList(this.pageSize, this.pageIndex);
          },
          err => {
            this.router.navigate(['/login']);
          });
      });
  }

  ngOnDestroy(): void {
    this.httpSubscription.unsubscribe();
  }

  private decision(params: any): any {
    const keyword = params['keyword'];
    if (this.router.url.indexOf('draft') >= 0) {
      const userId = +localStorage.getItem('userLoggedId');
      return this.tutorialService.getDraftsTutorialsForUser(userId, keyword);
    } else {
      return this.tutorialService.getTutorials(keyword);
    }
  }

  // addTutorialRouterLink(): void {
  //   location.replace(this.rootConst.FRONT_ADD_TUTORIAL);
  // }

  filterByKeyword(keyword: string) {
    const queryParams: Params = Object.assign({}, this.route.snapshot.queryParams);
    queryParams['keyword'] = keyword;
    queryParams['page'] = 0;
    if ((this.route.snapshot.url.toString()).indexOf('draft') >= 0) {
      this.router.navigate(['/tutorials/draft'], {queryParams: queryParams});
    } else {
      this.router.navigate(['/tutorials'], {queryParams: queryParams});
    }
  }

  public getServerData(event?: PageEvent) {
    const queryParams: Params = Object.assign({}, this.route.snapshot.queryParams);

    this.pageSize = event.pageSize;
    queryParams['page'] = event.pageIndex;
    queryParams['pageSize'] = event.pageSize;
    let root = '/';
    this.route.snapshot.pathFromRoot.forEach(el => root = root.concat(el.url.toString()));
    root = root.replace(',', '/');
    this.router.navigate([root], {queryParams: queryParams});
  }

  public initTutorialsPerPageList(pageSize: number, pageIndex: number) {
    this.tutorialsPerPage = [];
    this.tutorialsPerPage = this.tutorials.slice(pageIndex * pageSize, ((+pageIndex + 1) * pageSize));
  }

  deleteTutorial(idTutorial: number) {
    if (confirm('Do you want to delete this tutorial?')) {
      this.tutorialService.deleteTutorial(idTutorial).subscribe(
        tutorials => {
          this.tutorials = tutorials;
          this.initTutorialsPerPageList(this.pageSize, this.pageIndex);
        },
        err => {
          this.snackBarMessagePopup(err.error.message, 'Close');
        });

    }
  }

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }
}
