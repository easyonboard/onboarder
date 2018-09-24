import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {RootConst} from '../../util/RootConst';

@Injectable()
export class AuthService {
  private rootConst = new RootConst();

  constructor(private http: HttpClient) {
  }

  attemptAuth(ussername: string, password: string): Observable<any> {
    const credentials = {username: ussername, password: password};
    console.log('attempAuth ::');

    return this.http.post<any>(this.rootConst.GENERATE_TOKEN_URL, credentials);
  }
}
