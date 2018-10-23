import {AfterContentInit, Component, ElementRef, OnInit} from '@angular/core';
import {User} from '../domain/user';
import {ServerURLs} from '../util/ServerURLs';
import {Router} from '@angular/router';
import {LocalStorageConst} from '../util/LocalStorageConst';
import {AuthService} from '../common/core-auth/auth.service';
import {TokenStorage} from '../common/core-auth/token.storage';
import {UserService} from '../service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit, AfterContentInit {

  public option: boolean;
  public userLogged: User;
  public message: string;
  public rootConst: ServerURLs;
  private currentComponentElement: HTMLElement;
  public userNotfound: string;
  private headerDiv: NodeListOf<Element>;
  private footerDiv: Element;

  constructor(private authService: AuthService,
              private router: Router,
              private elemRef: ElementRef,
              private tokenStorage: TokenStorage,
              private userService: UserService) {
    this.option = false;
  }

  ngOnInit() {
    if (!this.tokenStorage.getToken()) {
      this.router.navigate(['/']);
    }
    this.userNotfound = '';
    this.message = 'Welcome!';
    this.rootConst = new ServerURLs();
  }

  ngAfterContentInit() {
    if (!this.tokenStorage.getToken()) {
      this.setVisibileHeaderAndFooter('hidden');
    } else {
      this.setVisibileHeaderAndFooter('visible');
    }
  }


  private setVisibileHeaderAndFooter(visility: string) {
    this.currentComponentElement = this.elemRef.nativeElement.previousElementSibling;
    this.footerDiv = this.currentComponentElement.parentElement.getElementsByClassName('footerDiv').item(0);

    this.headerDiv = this.currentComponentElement.getElementsByClassName('headerDiv');
    for (let index = 0; index < this.headerDiv.length; index++) {
      (<HTMLElement>this.headerDiv.item(index)).style.visibility = visility;
    }
    if (this.footerDiv != null) {
      (<HTMLElement>this.footerDiv).style.visibility = visility;
    }
  }

  login(username: string, password: string): void {
    username = username.trim();
    password = password.trim();
    this.authService.attemptAuth(username, password).subscribe(
      data => {
        this.tokenStorage.saveToken(data.token);
        this.setVisibileHeaderAndFooter('visible');
        this.userService.getUserByUsername(username).subscribe(user => {
          localStorage.setItem(LocalStorageConst._USER_FIRST_NAME, user.firstName);
          localStorage.setItem(LocalStorageConst._MSG_MAIL, user.msgMail);
          localStorage.setItem(LocalStorageConst._USER_ROLE, user.role.toString());
        });
        this.router.navigate(['/info']);
      }, error => {
        console.log(error);
      }
    );


  }
}
