import {Component, ElementRef} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from "@angular/router";
import {RootConst} from "./util/RootConst";
import {UserService} from "./user.service";
import {UtilityService} from "./utility.service";

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
  private profileButton: Element;


  constructor(private location: Location, private router: Router, private elemRef: ElementRef,private utilityService:UtilityService) {
    this.rootConst = new RootConst();
  }

  goBack(): void {
    this.location.back();
  }

  logout(): void {

    if (confirm("Do you really want to logout?")) {
      localStorage.removeItem("userLogged");
      location.replace(this.rootConst.LOGIN_PAGE);
    }
  }
  openModal(id:string){
    this.utilityService.openModal(id);
  }

}
