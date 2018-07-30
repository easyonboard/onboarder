import {Component, OnInit, ViewEncapsulation, OnDestroy} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {ActivatedRoute, Params, Router} from '@angular/router';
import {PageEvent} from '@angular/material';
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

  private httpSubscription: Subscription;
  private pageIndex = 0;

  constructor(private tutorialService: TutorialService,
              private route: ActivatedRoute,
              private router: Router) {
    this.rootConst = new RootConst();
  }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.route.queryParams.subscribe(
        queryParams => {
          this.pageIndex = +queryParams['page'];
          if (!this.pageIndex) {
            this.pageIndex = 0;
          }
          this.httpSubscription = this.decision(params).subscribe(tutorials => {
            this.tutorials = tutorials;
            this.initTutorialsPerPageList(this.pageSize, this.pageIndex);
          });
        }
      );
    });
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

  filterByKeyword(keyword: string) {
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
      this.tutorialService.deleteTutorial(idTutorial).subscribe(tutorials =>
        location.reload());
    }
  }
}
