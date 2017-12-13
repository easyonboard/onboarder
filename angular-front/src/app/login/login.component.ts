import {Component, OnInit} from '@angular/core';
import {UserService} from "../user.service";
import {UserDTO} from "../user";
import {RootConst} from "../util/RootConst";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public option: boolean;
  public userLogged: UserDTO;
  private message: string;
  private messageSignIn:string;
  public rootConst: RootConst;

  constructor(private userService: UserService, private router:Router) {
    this.option = false;
  }

  ngOnInit() {
    this.message = "Welcome back!";
    this.messageSignIn="Sign Up for Free";
    this.rootConst = new RootConst();
  }

  createNewAccount(option: boolean): void {

    this.option = option;
  }

  login(username: string, password: string): void {
    username = username.trim();
    password = password.trim();
    this.userService.login({username, password} as UserDTO).subscribe(res => {
        if(res.username.length>0){
                  localStorage.setItem("userLogged",res.username);
                  this.router.navigateByUrl(this.rootConst.REDIRECT_LOGIN_SUCCESS_URL);
             }
      },
      err => {
        if (err.status == 403) {
          this.message = "Username or password wrong. Please try again";
        }
      });

  }

  addUser(name:string, username: string, email:string, password: string, passwordII:string): void {
    name = name.trim();
    username = username.trim();
    email = email.trim();
    password=password.trim();
    passwordII=passwordII.trim();
    if(name==""){
      this.messageSignIn="Name can not be empty";
    }
    if(username==""){
      this.messageSignIn="Username can not be empty";
    }
    if(email==""){
      this.messageSignIn="Email can not be empty";
    }
    if(password=="" || passwordII==""){
      this.messageSignIn="Password can not be empty";
    }

    else{

      this.userService.addUser({name,username, email,password} as UserDTO).subscribe();
    }

  }
}
