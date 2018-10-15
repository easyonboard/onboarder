import {Component, Inject, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import {UserInformationService} from '../../service/user-information.service';
import {UserService} from '../../service/user.service';
import {User} from '../../domain/user';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {MAT_DIALOG_DATA, MatSelectChange} from '@angular/material';
import {Location} from '../../domain/location';
import {Department} from '../../domain/Department';

@Component({
  selector: 'app-user-info-formular',
  templateUrl: './user-info-formular.component.html',
  styleUrls: ['./user-info-formular.component.css']
})
export class UserInfoFormularComponent implements OnInit {
  [x: string]: any;
  public today: Date;

  public locations: string[];
  public locationDtos: Location[];
  @Input()
  show = true;
  @Input()
  userInformation = new User();

  public departments: Department[];

  public users$: Observable<User[]>;
  private searchTerms = new Subject<string>();

  constructor(private userInformationService: UserInformationService, private userService: UserService, @Inject(MAT_DIALOG_DATA) public userInfo: User) {
  }

  ngOnInit() {
    this.userInformationService.getAllDepartments().subscribe(resp=>this.departments=resp);
    this.today = new Date(Date.now());
    this.locations = [];
    if (this.userInfo != null) {
      this.userInformation = this.userInfo;
    }
    this.userInformationService.getAllLocations().subscribe(resp => {
      this.locationDtos = resp;
      resp.forEach(l => this.locations.push(l.locationName));
    }, error => this.snackBarMessagePopup(error.error.message, 'Close'));

    if (this.userInformation.mateUsername === undefined || this.userInformation.mateUsername === null) {

      this.userInformation.mateUsername = '';
    }

    if (this.userInformation.location === undefined || this.userInformation.location === null) {
      this.userInformation.location = new Location();
      this.userInformation.location.locationName = '';
    }

    this.users$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(300),
      // ignore new term if same as previous term
      distinctUntilChanged(),
      // switch to new search observable each time the term changes
      switchMap((term: string) => this.userService.searchUsers(term))
    );
  }

  search(term: string): void {
    this.searchTerms.next(term);
    this.show = true;
  }

  selectValue(event: MatSelectChange) {

    this.userInformation.department = event.value;
  }

  getDate(): Date {
    return new Date(this.userInformation.startDate);
  }

  selectLocationValue(event: MatSelectChange) {

    this.locationDtos.forEach(l => {
      if (l.locationName === event.value) {
        this.userInformation.location = l;
      }
    });
  }

  getUserBuddy(): String {
    return this.userInformation.mateUsername !== null ? this.userInformation.mateUsername : '';
  }
  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }
}
