import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {FrontURLs} from '../util/FrontURLs';

@Injectable()
export class LoggedInGuard implements CanActivate {
  constructor(private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (sessionStorage.getItem('AuthToken')) {
      return true;
    } else {
      this.router.navigateByUrl(FrontURLs.LOGIN_PAGE);
      return false;
    }


  }
}
