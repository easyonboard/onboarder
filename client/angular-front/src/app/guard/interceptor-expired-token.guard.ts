import {Injectable} from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable()
export class InterceptorExpiredTokenGuard implements CanActivate {
  jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private router: Router) {
  }

  canActivate(next: ActivatedRouteSnapshot,
              state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> | boolean {
    if (this.jwtHelper.isTokenExpired(sessionStorage.getItem('AuthToken'))) {
      localStorage.clear();
      this.router.navigateByUrl('/login');
      return false;
    } else {
      return true;
    }
  }
}
