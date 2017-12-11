import { Component, OnInit } from '@angular/core';
import {UserService} from "../user.service";
import { UserDTO} from "../user";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  public option:boolean;
  public userLogged: UserDTO;
  constructor(private userService:UserService) {
    this.option=false;
  }

  ngOnInit() {
  }

  createNewAccount(option:boolean): void {

    this.option=option;
  }

  login(username:string, password:string):void{
    username=username.trim();
    password=password.trim();
    this.userService.login({username,password} as UserDTO).subscribe(result=>{
      if(result.username.length>0){
        localStorage.setItem("userLogged",result.username);
      }
    });

  }

}
