import {
  HttpErrorResponse,
  HttpEvent, HttpHandler, HttpHeaderResponse, HttpHeaders, HttpInterceptor, HttpProgressEvent, HttpRequest, HttpResponse,
  HttpSentEvent, HttpUserEvent
} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {TokenStorage} from './token.storage';
import {Router} from '@angular/router';
import 'rxjs/add/operator/do';
import {FrontURLs} from '../../util/FrontURLs';

const TOKEN_HEADER_KEY = 'Authorization';
const BEARER_STRING = 'Bearer ';

@Injectable()
export class Interceptor implements HttpInterceptor {

  constructor(private token: TokenStorage, private router: Router) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpSentEvent | HttpHeaderResponse |
    HttpProgressEvent | HttpResponse<any> | HttpUserEvent<any>> {
    let authReq = req;
    if (this.token.getToken() !== null) {
      authReq = req.clone({headers: req.headers.set(TOKEN_HEADER_KEY, BEARER_STRING + this.token.getToken())});
    }
    return next.handle(authReq).do(
      (err: any) => {
        if (err instanceof HttpErrorResponse) {
          console.log(err);
          console.log('req url :: ' + req.url);
          if (err.status === 401) {
            this.router.navigate([FrontURLs.LOGIN_PAGE]);
          }
        }
      }
    );
  }
}
