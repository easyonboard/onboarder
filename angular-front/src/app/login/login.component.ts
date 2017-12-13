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
  public rootConst: RootConst;

  constructor(private userService: UserService, private router:Router) {
    this.option = false;
  }

  ngOnInit() {
    this.message = "Welcome back!";
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


}
