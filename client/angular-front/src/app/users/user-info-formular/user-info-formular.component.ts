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
  user = new User();

  public departments: Department[];

  public users$: Observable<User[]>;
  private searchTerms = new Subject<string>();

  constructor(private userInformationService: UserInformationService, private userService: UserService) {
  }

  ngOnInit() {
    this.userInformationService.getAllDepartments().subscribe(resp => this.departments = resp);
    this.today = new Date(Date.now());
    this.locations = [];
    if (this.userInfo != null) {
      this.user = this.userInfo;
    }
    this.userInformationService.getAllLocations().subscribe(resp => {
      this.locationDtos = resp;
      resp.forEach(l => this.locations.push(l.locationName));
      console.log(this.locations);
    }, error => this.snackBarMessagePopup(error.error.message, 'Close'));

    if (this.user.mate === undefined || this.user.mate === null) {
      this.user.mate = new User();
      this.user.mate.name = '';
    }

    if (this.user.location === undefined || this.user.location === null) {
      this.user.location = new Location();
      this.user.location.locationName = '';
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

    this.user.department = event.value;
  }

  getDate(): Date {
    return new Date(this.user.startDate);
  }

  selectLocationValue(event: MatSelectChange) {
    this.locationDtos.forEach(l => {
      if (l.locationName === event.value) {
        this.user.location = l;
      }
    });
  }

  getUserMate(): String {
    return this.user.mate !== null ? this.user.mate.name : '';
  }

  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }
}
