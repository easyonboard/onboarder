export class LocalStorageConst {
  static _USER_FIRST_NAME = 'firstName';
  static _DEMO_ENABLED = 'demo_enabled';
  static _MSG_MAIL = 'msgMail';
  static _USER_ROLE = 'userRole';
  static _USER_LOGGED = 'userLogged';

  static _ENABLED = 'enabled';
  static _DISABLED = 'disabled';

  // return the state of the show button
  static toggle(demo_enabled: string): boolean {
    if (demo_enabled === this._ENABLED) {
      localStorage.setItem(this._DEMO_ENABLED, this._DISABLED);
    } else {
      localStorage.setItem(this._DEMO_ENABLED, this._ENABLED);
    }

    return (localStorage.getItem(this._DEMO_ENABLED) === this._ENABLED);
  }

  static get IS_DEMO_ENABLED(): boolean {
    return (localStorage.getItem(this._DEMO_ENABLED) === this._ENABLED);
  }
}
