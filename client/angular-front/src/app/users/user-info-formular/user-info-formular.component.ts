import {Component, Inject, Input, OnInit} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';

import {UserInformationService} from '../../service/user-information.service';
import {UserService} from '../../service/user.service';
import {UserDTO} from '../../domain/user';
import {DepartmentType} from '../../domain/departmentType';

import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {MAT_DIALOG_DATA, MatSelectChange} from '@angular/material';
import {LocationDTO} from '../../domain/location';

@Component({
  selector: 'app-user-info-formular',
  templateUrl: './user-info-formular.component.html',
  styleUrls: ['./user-info-formular.component.css']
})
export class UserInfoFormularComponent implements OnInit {
  [x: string]: any;
  public today: Date;

  public locations: string[];
  public locationDtos: LocationDTO[];
  @Input()
  show = true;
  @Input()
  userInformation = new UserDTO();

  public departments: string[];

  public users$: Observable<UserDTO[]>;
  private searchTerms = new Subject<string>();

  constructor(private userInformationService: UserInformationService, private userService: UserService, @Inject(MAT_DIALOG_DATA) public userInfo: UserDTO) {
  }

  ngOnInit() {
    this.departments = Object.keys(DepartmentType);
    this.today = new Date(Date.now());
    this.locations = [];
    if (this.userInfo != null) {
      this.userInformation = this.userInfo;
    }
    this.userInformationService.getAllLocations().subscribe(resp => {
      this.locationDtos = resp;
      resp.forEach(l => this.locations.push(l.locationName));
    }, error => this.snackBarMessagePopup(error.error.message, 'Close'));

    if (this.userInformation.buddyUser === undefined || this.userInformation.buddyUser === null) {
      this.userInformation.buddyUser = new UserDTO();
      this.userInformation.buddyUser.name = '';
    }

    if (this.userInformation.location === undefined || this.userInformation.location === null) {
      this.userInformation.location = new LocationDTO();
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
    return this.userInformation.buddyUser !== null ? this.userInformation.buddyUser.name : '';
  }
  snackBarMessagePopup(message: string, action: string) {
    this.snackBar.open(message, action, {
      duration: 6000
    });
  }
}
