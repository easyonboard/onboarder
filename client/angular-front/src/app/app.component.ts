import {Component, ElementRef} from '@angular/core';
import {Location} from '@angular/common';
import {Router} from "@angular/router";
import {RootConst} from "./util/RootConst";
import {UserService} from "./service/user.service";
import {UtilityService} from "./service/utility.service";
import {UserDTO} from "./domain/user";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'
  ]
})
export class AppComponent {
  private rootConst: RootConst;
  public message:string;
  public successMessage:string;
  public username: String;

  constructor(private location: Location, private router: Router, private elemRef: ElementRef,private utilityService:UtilityService, private userService: UserService) {
    this.rootConst = new RootConst();
    this.message="";
    this.successMessage="";
  }

  goBack(): void {
    if(location.href.includes("subject")){
      this.location.back();
      return;
    }
    if(location.href.includes(this.rootConst.FRONT_DETAILED_COURSE)){
      this.router.navigateByUrl(this.rootConst.FRONT_REDIRECT_LOGIN_SUCCESS_URL);
      return;
    }
    this.location.back();
  }

  logout(): void {
    if (confirm("Do you really want to logout?")) {
      localStorage.removeItem("userLogged");
      this.redirectToLoginPage()
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

  userIsLogged():boolean {
    this.username = localStorage.getItem("userLogged");
    if (this.username!==null){
      return true;
    }
    return false;
  }

  redirectToLoginPage():void {
    location.replace(this.rootConst.FRONT_LOGIN_PAGE);
  }
}
