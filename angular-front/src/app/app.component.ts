import {Component, ElementRef} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from "@angular/router";
import {RootConst} from "./util/RootConst";
import {UserService} from "./user.service";
import {UtilityService} from "./utility.service";
import {UserDTO} from "./domain/user";

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
  public message:string;
  public successMessage:string;

  constructor(private location: Location, private router: Router, private elemRef: ElementRef,private utilityService:UtilityService, private userService: UserService) {
    this.rootConst = new RootConst();
    this.message="";
    this.successMessage="";
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

  closeModal(id:string){
    this.utilityService.closeModal(id);
  }

  updateUser(name: string, email: string, password: string, passwordII: string): void {
    name = name.trim();
    email = email.trim();
    password=password.trim();
    passwordII=passwordII.trim();


    if(password!="" && (password!=passwordII || password.length<6)){
      this.message="Password not matching or does not have 6 characters"
      return;
    }
    else{
      var username = localStorage.getItem("userLogged");
      if(name=="")
        name=null;
      if(email=="")
        email=null;
      if(password=="")
        password=null;
      this.userService.updateUser({name,username, email,password} as UserDTO).subscribe(
        res => {
          this.utilityService.closeModal('myModal');
          this.successMessage="Operatia a fost realizata cu success!"
          this.utilityService.openModal('myCustom');
          this.message="";
        },
        err => {
          this.message=err.error.message;
        });

    }

  }
}
