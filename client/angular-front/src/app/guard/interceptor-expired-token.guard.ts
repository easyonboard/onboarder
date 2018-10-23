import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {JwtHelperService} from '@auth0/angular-jwt';
import {FrontURLs} from '../util/FrontURLs';

@Injectable()
export class InterceptorExpiredTokenGuard implements CanActivate {
  jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.jwtHelper.isTokenExpired(sessionStorage.getItem('AuthToken'))) {
      localStorage.clear();
      sessionStorage.clear();
      this.router.navigateByUrl(FrontURLs.LOGIN_PAGE);
      return false;
    } else {
      return true;
    }
  }
}
