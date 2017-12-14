import {Component, ElementRef} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from "@angular/router";
import {RootConst} from "./util/RootConst";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'
  ]
})
export class AppComponent {
  title = 'app';
  private rootConst: RootConst;
  private currentComponentElement: HTMLElement;
  private backButton: Element;
  private logoutButton: Element;


  constructor(private location: Location, private router: Router, private elemRef: ElementRef) {
    this.rootConst = new RootConst();
  }

  goBack(): void {
    this.location.back();
  }

  logout(): void {
    localStorage.removeItem("userLogged");
    if (confirm("Do you really want to logout?")) {
      location.replace(this.rootConst.LOGIN_PAGE);
    }
  }
}
