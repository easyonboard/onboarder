import {AfterContentInit, Component, ElementRef, OnInit,} from '@angular/core';
import {UserService} from "../service/user.service";
import {UserDTO} from "../domain/user";
import {RootConst} from "../util/RootConst";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterContentInit {

  public option: boolean;
  public userLogged: UserDTO;
  private message: string;
  private messageSignIn: string;
  private errorMessage: string;
  public rootConst: RootConst;
  private currentComponentElement: HTMLElement;
  private backButton: Element;
  private logoutButton: Element;
  private profileButton: Element;
  private userNotfound: string;
  constructor(private userService: UserService, private router: Router, private elemRef: ElementRef) {
    this.option = false;
  }

  ngOnInit() {
    this.userNotfound="";
    this.message = "Welcome back!";
    this.messageSignIn = "Sign Up for Free";
    this.rootConst = new RootConst();

  }

  ngAfterContentInit() {
    this.currentComponentElement = this.elemRef.nativeElement.previousElementSibling;
    this.backButton = this.currentComponentElement.getElementsByClassName("back").item(0);
    this.logoutButton = this.currentComponentElement.getElementsByClassName("logOut").item(0);
    this.profileButton = this.currentComponentElement.getElementsByClassName("edit").item(0);
    var footer = this.currentComponentElement.parentElement.getElementsByClassName("footerDiv").item(0)
    console.log(footer)
    if (this.logoutButton !== null && this.backButton !== null) {
      this.currentComponentElement.removeChild(this.backButton);
      this.currentComponentElement.removeChild(this.logoutButton);
      this.currentComponentElement.removeChild(this.profileButton);
      this.currentComponentElement.parentElement.removeChild(footer);

    }
  }

  createNewAccount(option: boolean): void {
    if(true===option) {
      document.getElementById("signUp").style.backgroundColor = "#841439";
      document.getElementById("login").style.background = "#d3d3d3";
    }else {
      document.getElementById("signUp").style.backgroundColor = "#d3d3d3";
      document.getElementById("login").style.backgroundColor = "#841439";
    }
    this.option = option;
  }

  login(username: string, password: string): void {
    username = username.trim();
    password = password.trim();
    this.userService.login({username, password} as UserDTO).subscribe(res => {
        if (res.username.length > 0) {
          localStorage.setItem("userLogged", res.username);
          localStorage.setItem("userLoggedId", res.idUser.toString());
          this.currentComponentElement = this.elemRef.nativeElement.parentElement;
          if (this.logoutButton !== null && this.backButton !== null) {
            this.currentComponentElement.appendChild(this.backButton);
            this.currentComponentElement.appendChild(this.logoutButton);
            this.currentComponentElement.appendChild(this.profileButton);
          }
          this.router.navigateByUrl(this.rootConst.FRONT_REDIRECT_LOGIN_SUCCESS_URL);
        }
      },
      err => {
        if (err.status == 403) {
          this.userNotfound =  "Username or password wrong! Please try again.";
        }
        console.log(err);
      });

  }

  addUser(name: string, username: string, email: string, password: string, passwordII: string): void {
    name = name.trim();
    username = username.trim();
    email = email.trim();
    password = password.trim();
    passwordII = passwordII.trim();
    if (password != passwordII) {
      this.errorMessage = "Password does not match";
      return ;
    }

    else {
      this.userService.addUser({name, username, email, password} as UserDTO).subscribe(
        res => {

          this.option = false;

        },
        err => {
          this.errorMessage = err.error.message;
        });

    }
  }
}
