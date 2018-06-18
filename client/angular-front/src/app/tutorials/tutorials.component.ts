import {Component, OnInit} from '@angular/core';
import {RootConst} from '../util/RootConst';
import {TutorialDTO} from '../domain/tutorial';
import {TutorialService} from '../service/tutorial.service';
import {ActivatedRoute, Router} from '@angular/router';
import {PageEvent} from '@angular/material';

@Component({
  selector: 'app-tutorials',
  templateUrl: './tutorials.component.html',
  styleUrls: ['./tutorials.component.css']
})
export class TutorialsComponent implements OnInit {

  private rootConst: RootConst;
  tutorials: TutorialDTO[];
  tutorialsPerPage: TutorialDTO[];
  pageEvent: PageEvent;
  length = 100;
  pageSize = 10;
  pageSizeOptions = [5, 10, 25, 100];

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
    this.tutorialsPerPage = [];
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
    console.log(event.pageIndex);
    console.log(event.pageSize);
    let index = event.pageIndex;
    let pageSize = event.pageSize;
    let indexList = index * pageSize;
    for (indexList; indexList < (index + 1) * pageSize; indexList++) {
      console.log(this.tutorials[indexList]);
      if(this.tutorials[indexList] != null)
      this.tutorialsPerPage.push(this.tutorials[indexList]);
    }
    console.log(this.tutorialsPerPage);

  }
}
