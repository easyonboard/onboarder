import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {ServerURLs} from '../../util/ServerURLs';
import {FrontURLs} from '../../util/FrontURLs';

@Injectable()
export class AuthService {
  private rootConst = new ServerURLs();

  constructor(private http: HttpClient) {
  }

  attemptAuth(ussername: string, password: string): Observable<any> {
    const credentials = {username: ussername, password: password};
    console.log('attempAuth ::');

    return this.http.post<any>(ServerURLs.GENERATE_TOKEN_URL, credentials);
  }
}
