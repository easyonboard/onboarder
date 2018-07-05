export class SessionConst {
    static _DEMO_ENABLED = 'demo_enabled';
    static _USER_FIRSTNAME = 'userFirstname';
    static _MSG_MAIL = 'msgMail';
    static _USER_ROLE = 'userRole';
    static _USER_LOGGED_ID = 'userLoggedId';
    static _USER_LOGGED = 'userLogged';

    static _ENABLED = 'enabled';
    static _DISABLED = 'disabled';

    static toggle(demo_enabled: string): void {
      if (demo_enabled === this._ENABLED) {
        localStorage.setItem(this._DEMO_ENABLED, this._DISABLED);
      } else {
        localStorage.setItem(this._DEMO_ENABLED, this._ENABLED);
      }
    }

    static get IS_DEMO_ENABLED(): string {
      return localStorage.getItem(this._DEMO_ENABLED);
    }
  }
