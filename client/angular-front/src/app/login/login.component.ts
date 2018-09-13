import {AfterContentInit, Component, ElementRef, OnInit} from '@angular/core';
import {UserDTO} from '../domain/user';
import {RootConst} from '../util/RootConst';
import {Router} from '@angular/router';
import {LocalStorageConst} from '../util/LocalStorageConst';
import {AuthService} from '../common/core-auth/auth.service';
import {TokenStorage} from '../common/core-auth/token.storage';

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

  constructor(private authService: AuthService,
              private router: Router,
              private elemRef: ElementRef,
              private tokenStorage: TokenStorage) {
    this.option = false;
  }

  ngOnInit() {
    if (!this.tokenStorage.getToken()) {
      this.router.navigate(['/']);
    }
    this.userNotfound = '';
    this.message = 'Welcome!';
    this.rootConst = new RootConst();
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
        localStorage.setItem(LocalStorageConst._USER_USERNAME, username);
        this.setVisibileHeaderAndFooter('visible');
        this.router.navigate(['/info']);
      }, error => {
        console.log(error);
      }
    );
    // this.userService.login({username, password} as UserDTO).subscribe((res: UserDTO) => {
    //     if (res.username.length > 0) {
    //       localStorage.setItem(LocalStorageConst._USER_LOGGED, res.username);
    //       localStorage.setItem(LocalStorageConst._USER_LOGGED_ID, res.idUser.toString());
    //       localStorage.setItem(LocalStorageConst._USER_ROLE, res.role.role);
    //       localStorage.setItem(LocalStorageConst._MSG_MAIL, res.msgMail);
    //       localStorage.setItem(LocalStorageConst._USER_FIRSTNAME, res.name.split(' ')[0]);
    //       localStorage.setItem(LocalStorageConst._DEMO_ENABLED, LocalStorageConst._DISABLED);
    //       for (let index = 0; index < this.headerDiv.length; index++) {
    //         (<HTMLElement>this.headerDiv.item(index)).style.visibility = 'visible';
    //       }
    //       if (this.footerDiv != null) {
    //         (<HTMLElement>this.footerDiv).style.visibility = 'visible';
    //       }
    //       this.router.navigateByUrl(this.rootConst.FRONT_INFOS_PAGE);
    //       if (username === password) {
    //         this.router.navigateByUrl(this.rootConst.FRONT_INFOS_PAGE);
    //         this.snackBar.open('Please change your password', null, {
    //           duration: 4000
    //         });
    //         this.commonService.openEditProfileDialog();
    //       }
    //     }
    //   },
    //   err => {
    //     if (err.status === 403) {
    //       this.userNotfound = 'Username or password wrong! Please try again.';
    //     }
    //     console.log(err);
    //   });

  }
}
