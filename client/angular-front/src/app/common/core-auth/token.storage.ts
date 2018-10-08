import {Injectable} from '@angular/core';


const TOKEN_KEY = 'AuthToken';
const TOKEN_HEADER_KEY = 'Authorization';
const BEARER_STRING = 'Bearer ';

@Injectable()
export class TokenStorage {

  constructor() {
  }

  signOut() {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.clear();
  }

  public saveToken(token: string) {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public getTokenBearerString(): string {
    return BEARER_STRING;
  }
}
