import {AfterContentInit, Component, ElementRef, OnInit} from '@angular/core';
import {UserService} from '../service/user.service';
import {UserDTO} from '../domain/user';
import {RootConst} from '../util/RootConst';
import {Router} from '@angular/router';
import {CommonComponentsService} from '../common/common-components.service';
import {MatSnackBar} from '@angular/material';
import {LocalStorageConst} from '../util/LocalStorageConst';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterContentInit {

  public option: boolean;
  public userLogged: UserDTO;
  public message: string;
  private errorMessage: string;
  public rootConst: RootConst;
  private currentComponentElement: HTMLElement;
  public userNotfound: string;
  private headerDiv: NodeListOf<Element>;
  private footerDiv: Element;

  constructor(private userService: UserService, private router: Router, private elemRef: ElementRef,
              private commonService: CommonComponentsService, private snackBar: MatSnackBar) {
    this.option = false;
  }

  ngOnInit() {
    this.userNotfound = '';
    this.message = 'Welcome!';
    this.rootConst = new RootConst();
  }

  ngAfterContentInit() {
    this.currentComponentElement = this.elemRef.nativeElement.previousElementSibling;
    this.footerDiv = this.currentComponentElement.parentElement.getElementsByClassName('footerDiv').item(0);

    this.headerDiv = this.currentComponentElement.getElementsByClassName('headerDiv');
    for (let index = 0; index < this.headerDiv.length; index++) {
      (<HTMLElement>this.headerDiv.item(index)).style.visibility = 'hidden';
    }
    if (this.footerDiv != null) {
      (<HTMLElement>this.footerDiv).style.visibility = 'hidden';
    }
  }


  login(username: string, password: string): void {
    username = username.trim();
    password = password.trim();
    this.userService.login({username, password} as UserDTO).subscribe((res: UserDTO) => {
        if (res.username.length > 0) {
          localStorage.setItem(LocalStorageConst._USER_LOGGED, res.username);
          localStorage.setItem(LocalStorageConst._USER_LOGGED_ID, res.idUser.toString());
          localStorage.setItem(LocalStorageConst._USER_ROLE, res.role.role);
          localStorage.setItem(LocalStorageConst._MSG_MAIL, res.msgMail);
          localStorage.setItem(LocalStorageConst._USER_FIRSTNAME, res.name.split(' ')[0]);
          localStorage.setItem(LocalStorageConst._DEMO_ENABLED, LocalStorageConst._DISABLED);
          for (let index = 0; index < this.headerDiv.length; index++) {
            (<HTMLElement>this.headerDiv.item(index)).style.visibility = 'visible';
          }
          if (this.footerDiv != null) {
            (<HTMLElement>this.footerDiv).style.visibility = 'visible';
          }
          this.router.navigateByUrl(this.rootConst.FRONT_INFOS_PAGE);
          if (username === password) {
            this.router.navigateByUrl(this.rootConst.FRONT_INFOS_PAGE);
            this.snackBar.open('Please change your password', null, {
              duration: 4000
            });
            this.commonService.openEditProfileDialog();
          }
        }
      },
      err => {
        if (err.status === 403) {
          this.userNotfound = 'Username or password wrong! Please try again.';
        }
        console.log(err);
      });

  }
}
